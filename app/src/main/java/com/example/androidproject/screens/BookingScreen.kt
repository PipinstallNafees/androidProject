package com.example.androidproject.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidproject.data.Movie

@Composable
fun BookingScreen() {
    var movies by remember {
        mutableStateOf(
            listOf(
                Movie(1, "Oppenheimer", "July 15, 2025", 20),
                Movie(2, "Interstellar", "July 16, 2025", 15),
                Movie(3, "Dune Part 2", "July 17, 2025", 10)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .padding(16.dp)
    ) {
        Text(
            text = "🎟️ Book Your Tickets",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1E88E5),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(movies) { movie ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📅 Date: ${movie.date}", style = MaterialTheme.typography.bodyMedium)
                        Text("💺 Seats Left: ${movie.seatsAvailable}", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                movies = movies.map {
                                    if (it.id == movie.id && it.seatsAvailable > 0)
                                        it.copy(seatsAvailable = it.seatsAvailable - 1)
                                    else it
                                }
                            },
                            enabled = movie.seatsAvailable > 0,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Book Now")
                        }
                    }
                }
            }
        }
    }
}
