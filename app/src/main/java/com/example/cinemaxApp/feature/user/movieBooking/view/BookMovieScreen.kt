package com.example.cinemaxApp.feature.user.movieBooking.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

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
        Text(
            text = "Details Movie",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
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
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Director: Destin Daniel Cretton",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⭐", fontSize = 16.sp)
                Text(text = "4.9", color = Color(0xFFFFA000), fontWeight = FontWeight.Medium)
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
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Text(text = "Plagued by a violent and recurring nightmare, a college student heads home to track down the one person who might be able to break the cycle of death and save her family from the grisly demise that inevitably awaits them all",
                        style = MaterialTheme . typography . bodySmall, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { nav.navigate(Screen.AddAttendee.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !viewModel.isBookedState,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448AFF))
            ) {
                Text(
                    text = if (!viewModel.isBookedState) "Book Ticket" else "Already Booked",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Text("No Movie Available")
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1E1E1E).copy(alpha = 0.1f),
        modifier = Modifier.height(32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(text = text, fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}

@Composable
fun RemoteImageFromUrl(
    url: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val finalUrl = if (url.isNullOrBlank()) {
        "https://www.prokerala.com/movies/assets/img/no-poster-available.jpg"
    } else {
        url
    }

    LaunchedEffect(finalUrl) {
        withContext(Dispatchers.IO) {
            try {
                val stream = URL(finalUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(stream)
                imageBitmap = bitmap?.asImageBitmap()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = "Movie Poster",
            modifier = modifier,
            contentScale = contentScale
        )
    } ?: Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
