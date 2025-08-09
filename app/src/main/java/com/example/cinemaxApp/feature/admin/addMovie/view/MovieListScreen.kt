package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModelFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun MovieListScreen(nav: NavHostController, viewModel: MovieAdminViewModel) {
    var movieToDelete by remember { mutableStateOf<Movie?>(null) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    LaunchedEffect(Unit) {
        viewModel.getMovieList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF23232F))
                )
            )
//            .padding(20.dp)
            .padding(horizontal = 25.dp, vertical = 40.dp)
    ) {
//        Text(
//            text = "🎬 Movie Management",
//            style = MaterialTheme.typography.headlineMedium.copy(
//                fontFamily = FontFamily.Serif,
//                fontWeight = FontWeight.Bold,
//                fontSize = 28.sp,
//                color = Color.White
//            ),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )

        MovieListTopBar({ nav.popBackStack() })

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { nav.navigate(Screen.CreateMovie.route) },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C))
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Add Movie", color = Color.White)
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.movieList) { movie ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    // Formatters for input
                    val inputDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val inputTimeFormatter = DateTimeFormatter.ofPattern("HH:mm") // 24-hour format

                    // Parse the input
                    val date = LocalDate.parse(movie.date, inputDateFormatter)
                    val time = LocalTime.parse(movie.time, inputTimeFormatter)

                    Column(Modifier.padding(20.dp)) {
                        Text(
                            movie.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            "🎟 Seats: ${movie.totalSeats} | Allocated: ${movie.bookedSeats}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            "📅 Date: ${date.format(dateFormatter)} | Time: ${time.format(timeFormatter)}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { nav.navigate(Screen.EditMovie.createRoute(movie.id)) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                                Spacer(Modifier.width(4.dp))
                                Text("Edit", color = Color.White)
                            }

                            Button(
                                onClick = { movieToDelete = movie },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAA0000))
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                                Spacer(Modifier.width(4.dp))
                                Text("Delete", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    if (movieToDelete != null) {
        AlertDialog(
            onDismissRequest = { movieToDelete = null },
            title = { Text("Delete Movie") },
            text = { Text("Are you sure you want to delete \"${movieToDelete?.title}\"? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteMovie(movieToDelete!!)
                        movieToDelete = null
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { movieToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
fun MovieListTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color.Transparent),
//            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Movie Management",
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
