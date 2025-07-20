package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cinemaxApp.feature.admin.addMovie.ViewModel.AdminViewModel

@Composable
fun EditMovieScreen(nav: NavController, vm: AdminViewModel, id: Int) {
    val movie = vm.getMovieById(id) ?: return

    var title by remember { mutableStateOf(movie.title) }
    var desc by remember { mutableStateOf(movie.description) }
    var seats by remember { mutableStateOf(movie.totalSeats.toString()) }

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
            value = seats,
            onValueChange = { seats = it },
            label = { Text("Total Seats") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (seats.toIntOrNull() != null) {
                    vm.updateMovie(movie.copy(title = title, description = desc, totalSeats = seats.toInt()))
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
