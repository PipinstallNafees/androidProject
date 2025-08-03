package com.example.cinemaxApp.feature.user.movieBooking.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
            .padding(horizontal = 25.dp, vertical = 40.dp)
    ) {
        MovieDetailsTopBar({ nav.popBackStack() })
//        Text(
//            text = "Details Movie",
//            style = MaterialTheme.typography.headlineSmall.copy(
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            ),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
        Spacer(modifier = Modifier.height(25.dp))

        if (movie != null) {
            viewModel.isBooked(movie.id)

            RemoteImageFromUrl(
                url = movie.posterUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                text = movie.description,
                maxLines = 3, // or any number of lines you want
                overflow = TextOverflow.Ellipsis,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // <-- this applies around the image
    ) {
        val fallbackUrl = "https://www.prokerala.com/movies/assets/img/no-poster-available.jpg"
        val imageUrl = url.takeIf { !it.isNullOrBlank() } ?: fallbackUrl

        Log.d("ImageUrl", imageUrl)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
//            .error(fallbackUrl)
                .build(),
            contentDescription = "Movie Poster",
            contentScale = contentScale,
            modifier = modifier.fillMaxSize()
                .aspectRatio(2f / 2.5f)
                .clip(RoundedCornerShape(11.dp))
        )
    }

//    val painter = rememberAsyncImagePainter(model = imageUrl)
//
//    when (painter.state) {
//        is AsyncImagePainter.State.Loading -> {
//            Log.d("ImageState", "Loading")
//            Box(modifier = modifier, contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(color = Color.White)
//            }
//        }
//        is AsyncImagePainter.State.Error -> {
//            Log.d("ImageState", "Fallback Poster")
//            AsyncImage(
//                model = fallbackUrl,
//                contentDescription = "Fallback Poster",
//                modifier = modifier,
//                contentScale = contentScale
//            )
//        }
//        else -> {
//            Log.d("ImageState", "Actual Poster")
//            Image(
//                painter = painter,
//                contentDescription = "Movie Poster",
//                modifier = modifier,
//                contentScale = contentScale
//            )
//        }
//    }
}

@Composable
fun MovieDetailsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Movie Details",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        // Left-aligned Back Icon
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}



