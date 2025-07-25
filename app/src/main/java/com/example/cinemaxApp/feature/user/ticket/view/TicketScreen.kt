package com.example.cinemaxApp.feature.user.ticket.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

@Composable
fun TicketQRCodeScreen() {
    val ticketJson = remember {
        JSONObject().apply {
            put("ticket_id", "TKT2025XYZ")
            put("name", "Mrityunjoy Sahoo")
            put("event", "AI Summit 2025")
            put("date", "2025-08-15")
            put("seat", "A21")
        }.toString()
    }

    val qrBitmap = remember { generateQRCode(ticketJson, 500) }

//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Your Ticket QR Code", fontSize = 20.sp)
//        Spacer(Modifier.height(20.dp))
//        Image(bitmap = qrBitmap.asImageBitmap(), contentDescription = "QR Code")
//    }
    TicketCard(
        film = "Final Destination",
        date = "15-08-2025",
        seats = "J12, J13",
        location = "Auditorium",
        time = "06:00 PM",
        paymentStatus = "Successful",
        orderId = "TKT2025XYZ",
        barcodeBitmap = qrBitmap.asImageBitmap()
    )
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

@Preview
@Composable
fun TicketQRCodeScreenPreview() {
    TicketQRCodeScreen()
}

@Composable
fun TicketCard(
    film: String,
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
            .padding(16.dp)
            .width(300.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1B1B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Film: $film", color = Color.White)
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

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Payment", color = Color.Gray, fontSize = 12.sp)
                    Text(paymentStatus, color = Color.White)
                }
                Column {
                    Text("Order", color = Color.Gray, fontSize = 12.sp)
                    Text(orderId, color = Color.White)
                }
            }

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

