package com.example.cinemaxApp.feature.user.movieBooking.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidproject.R
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel

@Composable
fun BookMovieScreen(nav: NavHostController, viewModel: UserBookingViewModel) {
    val movie = viewModel.movie

    LaunchedEffect(Unit) {
        viewModel.getMovie()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🎟 Movie Ticket Booking", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        if (movie != null) {
            viewModel.isBooked(movie.id)
            Image(
                painter = painterResource(id = R.drawable.bloodlines),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(Modifier.height(16.dp))

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("🎬 Movie: ${movie.title}")
                    Text("📜 Description: ${movie.description}")
                    Text("📅 Date: ${movie.date}")
                    Text("⏰ Time: ${movie.time}")
                    Text("🪑 Seats Left: ${movie.totalSeats-movie.bookedSeats}")
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { nav.navigate(Screen.AddAttendee.route) },
                        enabled = !viewModel.isBookedState
                    ) {
                        if (!viewModel.isBookedState){
                            Text("Book Ticket")
                        } else {
                            Text("Already Booked")
                        }
                    }
                }
            }
        } else {
            Text("No Movie")
        }
    }
}


