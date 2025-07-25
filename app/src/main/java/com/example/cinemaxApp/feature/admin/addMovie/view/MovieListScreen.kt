package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel

@Composable
fun MovieListScreen(nav: NavHostController, viewModel: MovieAdminViewModel) {
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
            .padding(20.dp)
    ) {
        Text(
            text = "🎬 Movie Management",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

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

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(viewModel.movieList) { movie ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
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
                                onClick = { nav.navigate(Screen.AllocateSeat.createRoute(movie.id)) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                                Spacer(Modifier.width(4.dp))
                                Text("Allocate", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
