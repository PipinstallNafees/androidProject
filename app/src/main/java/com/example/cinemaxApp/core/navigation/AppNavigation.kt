package com.example.cinemaxApp.core.navigation

import BookMovieScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.feature.admin.addMovie.ViewModel.AdminViewModel
import com.example.cinemaxApp.feature.admin.addMovie.model.Movie
import com.example.cinemaxApp.feature.admin.addMovie.view.AllocateSeatScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.CreateMovieScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.EditMovieScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.MovieListScreen
import com.example.cinemaxApp.feature.user.auth.view.View.AddAttendeeScreen
import com.example.cinemaxApp.feature.user.auth.view.ViewModel.UserBookingViewModel
import com.example.cinemaxApp.feature.user.auth.view.UserPage
import com.example.cinemaxApp.feature.user.social.Instagram
import com.example.cinemaxApp.feature.user.userDashboard.DashboardScreen

@Composable
fun MovieAppNav() {
    val nav = rememberNavController()
    val userViewModel = remember { UserBookingViewModel() }
    val adminViewModel = remember { AdminViewModel() }

    // Sample default movie
    LaunchedEffect(Unit) {
        userViewModel.selectedMovie = Movie(
            id = 1,
            title = "Final Destination: Bloodlines",
            posterUrl = "https://upload.wikimedia.org/wikipedia/en/f/fd6bloodlines.jpg",
            date = "2025-07-20",
            time = "6:30 PM",
            totalSeats = 10
        )
    }

    NavHost(navController = nav, startDestination = "movieList") {

        // Admin Flow
        composable("movieList") {
            MovieListScreen(nav = nav, vm = adminViewModel)
        }

        composable("createMovie") {
            CreateMovieScreen(nav = nav, vm = adminViewModel)
        }

        composable("editMovie/{id}") {
            val id = it.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            EditMovieScreen(nav = nav, vm = adminViewModel, id = id)
        }

        composable("allocateSeat/{id}") {
            val id = it.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            AllocateSeatScreen(nav = nav, vm = adminViewModel, id = id)
        }

        // User Flow
        composable("movieDetail") {
            BookMovieScreen(nav = nav, viewModel = userViewModel)
        }

        composable("attendeeForm") {
            AddAttendeeScreen(nav = nav, viewModel = userViewModel)
        }

        /*
        composable("dashboard") {
            DashboardScreen(nav)
        }
        composable("Instagram") {
            Instagram(nav)
        }
        composable("user") {
            UserPage(nav)
        }
        */
    }
}
