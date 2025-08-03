package com.example.cinemaxApp.feature.admin.addMovie.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CreateMovieScreen(nav: NavHostController, viewModel: MovieAdminViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var posterUrl by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }

    val isFormValid = title.isNotBlank() && seats.toIntOrNull() != null

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")


    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val pickedDate = LocalDate.of(year, month + 1, dayOfMonth)
                selectedDate = pickedDate.toString()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                val pickedTime = LocalTime.of(hour, minute)
                selectedTime = pickedTime.toString()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF23232F))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "➕ Create New Movie",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("🎬 Movie Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("📝 Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = seats, onValueChange = { seats = it }, label = { Text("🪑 Total Seats") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = director, onValueChange = { director = it }, label = { Text("🎬 Director") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("⭐ Rating") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = genre, onValueChange = { genre = it }, label = { Text("🎭 Genre") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = posterUrl, onValueChange = { posterUrl = it }, label = { Text("🌐 Poster Image URL") }, modifier = Modifier.fillMaxWidth())

                if (posterUrl.isNotBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(posterUrl),
                        contentDescription = "Poster Preview",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = {},
                    label = { Text("📅 Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    readOnly = true,
                    enabled = false
                )

                OutlinedTextField(
                    value = selectedTime,
                    onValueChange = {},
                    label = { Text("⏰ Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { timePickerDialog.show() },
                    readOnly = true,
                    enabled = false
                )

                Button(
                    onClick = {
                        if (isFormValid) {
                            viewModel.addMovie(
                                title,
                                desc,
                                seats.toInt(),
                                posterUrl,
                                selectedDate,
                                selectedTime,
                                genre,
                                rating,
                                director

                            )
                            nav.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C)),
                    enabled = isFormValid
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Create",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
