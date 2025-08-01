package com.example.cinemaxApp.feature.user.movieBooking.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
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
            .background(Color(0xFF121212)) // Royal Black background
            .padding(16.dp)
    ) {
        Text(
            text = "Details Movie",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (movie != null) {
            viewModel.isBooked(movie.id)

            RemoteImageFromUrl(
                url = movie.posterUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Director: Destin Daniel Cretton",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⭐", fontSize = 16.sp)
                Text(
                    text = "4.9",
                    color = Color(0xFFFFA000),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Chip(text = "Horror")
                Chip(text = "Mystery")
                Chip(text = "01h 50m")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Synopsis",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )

            Text(
                text = "Plagued by a violent and recurring nightmare, a college student heads home to track down the one person who might be able to break the cycle of death and save her family from the grisly demise that inevitably awaits them all.",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { nav.navigate(Screen.AddAttendee.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !viewModel.isBookedState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!viewModel.isBookedState) Color(0xFF448AFF) else Color(0xFF424242),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF424242),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = if (!viewModel.isBookedState) "Book Ticket" else "Already Booked",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Text("No Movie Available", color = Color.White)
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE0E0E0).copy(alpha = 0.2f),
        modifier = Modifier.height(32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(text = text, fontSize = 12.sp, color = Color.LightGray)
        }
    }
}

@Composable
fun RemoteImageFromUrl(
    url: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val fallbackUrl = "https://www.prokerala.com/movies/assets/img/no-poster-available.jpg"
    val imageUrl = url.takeIf { !it.isNullOrBlank() } ?: fallbackUrl

    val painter = rememberAsyncImagePainter(model = imageUrl)

    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        is AsyncImagePainter.State.Error -> {
            AsyncImage(
                model = fallbackUrl,
                contentDescription = "Fallback Poster",
                modifier = modifier,
                contentScale = contentScale
            )
        }
        else -> {
            Image(
                painter = painter,
                contentDescription = "Movie Poster",
                modifier = modifier,
                contentScale = contentScale
            )
        }
    }
}



