package com.example.cinemaxApp.core.navigation


import BookMovieScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaxApp.core.splashScreen.LandingPage
import com.example.cinemaxApp.feature.admin.AdminDashboard.AdminDashboardScreen
import com.example.cinemaxApp.feature.admin.addMovie.ViewModel.AdminViewModel
import com.example.cinemaxApp.feature.admin.addMovie.view.CreateMovieScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.MovieListScreen
import com.example.cinemaxApp.feature.user.auth.view.choiceSceen
import com.example.cinemaxApp.feature.admin.auth.view.Adminpage
import com.example.cinemaxApp.feature.user.auth.view.UserPage
import com.example.cinemaxApp.feature.user.auth.view.View.AddAttendeeScreen
import com.example.cinemaxApp.feature.user.auth.view.ViewModel.UserBookingViewModel
import com.example.cinemaxApp.feature.user.social.Instagram
import com.example.cinemaxApp.feature.user.userDashboard.DashboardScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            LandingPage(navController)
        }
        composable("choice") {
            choiceSceen(navController)
        }
        composable("admin") {
            Adminpage(navController)
        }
        composable("dashboard"){
            DashboardScreen(navController)
        }
        composable ("Instagram"){
            Instagram(navController)
        }
        composable("user") {
            UserPage(navController)
        }
        composable ("Booking"){
            val viewModel: UserBookingViewModel = viewModel()
            BookMovieScreen(navController, viewModel)
        }
        composable ("attendeeForm"){
            val viewModel: UserBookingViewModel = viewModel()
            AddAttendeeScreen(navController, viewModel)
        }
        composable ("Adminbooking"){
            val adminViewModel: AdminViewModel = viewModel()
            MovieListScreen(navController, adminViewModel)
        }
        composable("createMovie") {
            val adminViewModel: AdminViewModel = viewModel()
            CreateMovieScreen(navController, adminViewModel)
        }
        composable("adminDashboard") {
            AdminDashboardScreen(navController)
        }
    }
}

