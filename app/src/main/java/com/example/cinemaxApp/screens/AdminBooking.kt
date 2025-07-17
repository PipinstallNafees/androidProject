package com.example.cinemaxApp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemaxApp.data.Movie


@Composable
fun AdminBookingScreen() {
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
            .fillMaxWidth()
            .background(Color(0xFFF7F9FC))
            .padding(26.dp)
            .padding(top=20.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "🎟️ Book Your Tickets",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1E88E5),
            )
            Spacer(modifier = Modifier.weight(1f)) // Pushes the text to the start
            IconButton(
                onClick = { /* Handle logout action */ },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Logout",
                    tint = Color(0xFF1E88E5)

                )
            }
        }

        HorizontalDivider(thickness = 3.dp, color = Color(0xFFF50057), modifier = Modifier.padding(bottom = 5.dp))

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(top=20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

@Preview(showBackground = true)
@Composable
fun AdminBookingScreenPreview() {
    AdminBookingScreen()
}