package com.example.cinemaxApp.feature.admin.addMovie.view

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun EditMovieScreen(nav: NavHostController, viewModel: MovieAdminViewModel, id: String) {
    val movie = viewModel.getMovieById(id) ?: return

    var title by remember { mutableStateOf(movie.title) }
    var desc by remember { mutableStateOf(movie.description) }
    var posterUrl by remember { mutableStateOf(movie.posterUrl) }
    var totalSeats by remember { mutableStateOf(movie.totalSeats.toString()) }
    var date by remember { mutableStateOf(movie.date) }
    var time by remember { mutableStateOf(movie.time) }
    val context = LocalContext.current

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    val calendar = Calendar.getInstance()

    // Date Picker Dialog
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val pickedDate = LocalDate.of(year, month + 1, dayOfMonth)
                date = pickedDate.toString()
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
                time = pickedTime.toString()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // use 12-hour format with AM/PM
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "✏️ Edit Movie",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Movie Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Movie Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = posterUrl,
            onValueChange = { posterUrl = it },
            label = { Text("🖼 Poster URL (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = totalSeats,
            onValueChange = { totalSeats = it },
            label = { Text("Total Seats") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date?.format(dateFormatter) ?: "",
            onValueChange = {},
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { datePickerDialog.show() },
            enabled = false,
            readOnly = true
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = time?.format(timeFormatter) ?: "",
            onValueChange = {},
            label = { Text("Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { timePickerDialog.show() },
            enabled = false,
            readOnly = true
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (totalSeats.toIntOrNull() != null) {
                    viewModel.updateMovie(movie.copy(title = title, description = desc, posterUrl = posterUrl, totalSeats = totalSeats.toInt(), date = date, time = time))
                    nav.popBackStack()
                }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
            Spacer(Modifier.width(8.dp))
            Text("Update")
        }
    }
}
