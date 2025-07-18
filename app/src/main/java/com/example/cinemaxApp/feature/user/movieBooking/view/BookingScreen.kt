package com.example.cinemaxApp.feature.user.movieBooking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemaxApp.feature.admin.addMovie.model.Movie

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
            .background(Color(0xFF121212)) // Carbon black background
            .padding(26.dp)
            .padding(top = 30.dp)
    ) {
        Text(
            text = "🎟️ Book Your Tickets",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFF2F2F2), // Vibrant amber
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        HorizontalDivider(thickness = 2.dp, color = Color(0xFFBB86FC)) // Lavender accent

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), // Charcoal card
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "📅 Date: ${movie.date}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            color = Color.LightGray
                        )
                        Text(
                            text = "💺 Seats Left: ${movie.seatsAvailable}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            color = Color.LightGray
                        )

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
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF830F28)) // Deep orange
                        ) {
                            Text("Book Now", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    BookingScreen()
}
