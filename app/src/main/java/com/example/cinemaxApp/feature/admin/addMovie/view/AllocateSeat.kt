package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cinemaxApp.feature.admin.addMovie.ViewModel.AdminViewModel

@Composable
fun AllocateSeatScreen(nav: NavController, vm: AdminViewModel, id: Int) {
    val movie = vm.getMovieById(id) ?: return
    var seatNo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "🎟️ Allocate Seat - ${movie.title}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Allocated Seats: ${movie.allocatedSeats.sorted().joinToString()}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = seatNo,
            onValueChange = { seatNo = it },
            label = { Text("Enter Seat Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    val seat = seatNo.toIntOrNull()
                    if (seat != null) {
                        vm.allocateSeat(id, seat)
                        seatNo = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Icon(Icons.Default.Check, contentDescription = "Allocate")
                Spacer(Modifier.width(6.dp))
                Text("Allocate")
            }

            Button(
                onClick = { nav.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Icon(Icons.Default.Done, contentDescription = "Done") // ✅ SAFE ICON
                Spacer(Modifier.width(6.dp))
                Text("Done")
            }
        }
    }
}
