package com.example.cinemaxApp.feature.user.ticket.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.model.Ticket
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.profile.view.AdminProfileTopBar
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel
import com.example.cinemaxApp.feature.user.ticket.viewmodel.TicketViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter


@Preview
@Composable
fun TicketQRCodeScreenPreview() {
    TicketScreen(rememberNavController(), viewModel(), viewModel(), true)
}

@Composable
fun TicketScreen(nav: NavHostController, bookingViewModel: UserBookingViewModel , ticketViewModel: TicketViewModel, isNavFromSeatBooking: Boolean) {
    var ticketJson by remember { mutableStateOf("") }

    val movie: Movie?
    val ticket: Ticket?

    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    if (isNavFromSeatBooking) {
        movie = bookingViewModel.movie
        ticket = bookingViewModel.ticket
    } else {
        ticketViewModel.setData()
        movie = ticketViewModel.movie
        ticket = ticketViewModel.ticket
    }

    if (ticket != null) {
        ticketJson = JSONObject().apply {
            put("ticketId", ticket.id)
            put("movieName", movie?.title)
            put("date", movie?.date!!.format(dateFormatter)) // the incomming is like this
            put("time", movie?.time!!.format(timeFormatter)) // In this format
            put("seat", JSONArray(ticket.seats))
            put("AttendeeDet", JSONArray().apply {
                ticket.attendeeList!!.forEach { attendee ->
                    val jsonObject = JSONObject().apply {
                        put("branch", attendee.branch)
                        put("name", attendee.name)
                        put("sic", attendee.sic)
                    }
                    put(jsonObject)
//                    put(JSONObject(it))
                }
            })
        }.toString()

        val qrBitmap = generateQRCode(ticketJson, 500)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(horizontal = 25.dp, vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TicketTopBar({
                    nav.navigate(Screen.UserDashboard.route) {
                        popUpTo(Screen.UserDashboard.route) { inclusive = true }
                        launchSingleTop = true
                    }
                })
            }

            // Centered TicketCard
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TicketCard(
                    movie = movie!!.title,
                    date = movie?.date!!.format(dateFormatter),
                    seats = ticket.seats.joinToString(", "),
                    location = "Auditorium",
                    time = movie?.time!!.format(timeFormatter),
                    paymentStatus = "Successful",
                    orderId = ticket.id,
                    barcodeBitmap = qrBitmap.asImageBitmap()
                )
            }

            // Button pinned to bottom
            Button(
                onClick = {
                    nav.navigate(Screen.UserDashboard.route) {
                        popUpTo(Screen.UserDashboard.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCF6679),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to Home", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(horizontal = 25.dp, vertical = 40.dp)
        ) {
            TicketTopBar({ nav.popBackStack() })

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "No movie booked",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

fun generateQRCode(content: String, size: Int): Bitmap {
    val hints = hashMapOf<EncodeHintType, Any>().apply {
        put(EncodeHintType.CHARACTER_SET, "UTF-8")
    }
    val matrix = MultiFormatWriter().encode(
        content,
        BarcodeFormat.QR_CODE,
        size,
        size,
        hints
    )
    return BarcodeEncoder().createBitmap(matrix)
}

@Composable
fun TicketCard(
    movie: String,
    date: String,
    seats: String,
    location: String,
    time: String,
    paymentStatus: String,
    orderId: String,
    barcodeBitmap: ImageBitmap // pass generated barcode or placeholder
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .width(300.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF37383F))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Movie: $movie", color = Color.White,fontWeight = FontWeight.Bold, fontSize = 18.sp )
                Text("e-ticket", color = Color(0xFFE91E63), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Date", color = Color.Gray, fontSize = 12.sp)
                    Text(date, color = Color.White)
                }
                Column {
                    Text("Seats", color = Color.Gray, fontSize = 12.sp)
                    Text(seats, color = Color.White)
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Location", color = Color.Gray, fontSize = 12.sp)
                    Text(location, color = Color.White)
                }
                Column {
                    Text("Time", color = Color.Gray, fontSize = 12.sp)
                    Text(time, color = Color.White)
                }
            }

//            Spacer(Modifier.height(8.dp))
//
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column {
//                    Text("Payment", color = Color.Gray, fontSize = 12.sp)
//                    Text(paymentStatus, color = Color.White)
//                }
//                Column {
//                    Text("Order", color = Color.Gray, fontSize = 12.sp)
//                    Text(orderId, color = Color.White)
//                }
//            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color.Gray,
                        start = Offset.Zero,
                        end = Offset(size.width, 0f),
                        strokeWidth = 1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box (
                modifier = Modifier.clip(RoundedCornerShape(11.dp))
            ) {
                Image(
                    bitmap = barcodeBitmap,
                    contentDescription = "Barcode",
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
            }
        }

    }
}

fun saveTicketAsPdf(context: Context, composableBitmap: Bitmap, filename: String) {
    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(composableBitmap.width, composableBitmap.height, 1).create()
    val page = document.startPage(pageInfo)

    page.canvas.drawBitmap(composableBitmap, 0f, 0f, null)
    document.finishPage(page)

    val file = File(context.getExternalFilesDir(null), "$filename.pdf")
    document.writeTo(FileOutputStream(file))
    document.close()
}

//@Composable
//fun CaptureAndSaveTicket() {
//    val context = LocalContext.current
//    val density = LocalDensity.current
//    val bitmapRef = remember { mutableStateOf<Bitmap?>(null) }
//
//    AndroidViewBinding(factory = { inflater, parent, _ ->
//        val view = ComposeView(context)
//        view.setContent {
////            TicketCard( ... ) // Your data here
//        }
//
//        view
//    }, update = {
//        it.post {
//            val bitmap = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            it.draw(canvas)
//            bitmapRef.value = bitmap
//            saveTicketAsPdf(context, bitmap, "MyTicket")
//        }
//    })
//}

@Composable
fun TicketTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "E-Ticket",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        // Left-aligned Back Icon
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}