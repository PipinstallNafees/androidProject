package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import java.time.LocalDate
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun CreateMovieScreen(nav: NavHostController, viewModel: MovieAdminViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf("") }
    var posterUrl by remember { mutableStateOf("") }
    val isFormValid = title.isNotBlank() && seats.toIntOrNull() != null
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF23232F))
                )
            )
            .padding(24.dp),
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
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("🎬 Movie Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("📝 Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = seats,
                    onValueChange = { seats = it },
                    label = { Text("🪑 Total Seats") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = posterUrl,
                    onValueChange = { posterUrl = it },
                    label = { Text("🖼 Poster URL (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                val context = LocalContext.current

                val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
                val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

                val calendar = Calendar.getInstance()

                // Date Picker Dialog
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

                // Time Picker Dialog
                val timePickerDialog = remember {
                    TimePickerDialog(
                        context,
                        { _, hour: Int, minute: Int ->
                            val pickedTime = LocalTime.of(hour, minute)
                            selectedTime = pickedTime.toString()
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false // use 12-hour format with AM/PM
                    )
                }


                    OutlinedTextField(
                        value = selectedDate?.format(dateFormatter) ?: "",
                        onValueChange = {},
                        label = { Text("Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { datePickerDialog.show() },
                        enabled = false,
                        readOnly = true
                    )

                    OutlinedTextField(
                        value = selectedTime?.format(timeFormatter) ?: "",
                        onValueChange = {},
                        label = { Text("Time") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { timePickerDialog.show() },
                        enabled = false,
                        readOnly = true
                    )




                Button(
                    onClick = {
                        if (isFormValid) {
//                            coroutineScope.launch {}
                            viewModel.addMovie(title, desc, seats.toInt(), posterUrl, selectedDate, selectedTime)
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
