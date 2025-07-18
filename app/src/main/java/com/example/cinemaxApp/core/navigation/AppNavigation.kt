package com.example.cinemaxApp.core.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaxApp.feature.user.movieBooking.view.BookingScreen
import com.example.cinemaxApp.core.splashScreen.LandingPage
import com.example.cinemaxApp.feature.user.auth.view.choiceSceen
import com.example.cinemaxApp.feature.admin.auth.view.Adminpage

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

    }
}

