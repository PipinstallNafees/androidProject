package com.example.cinemaxApp.core.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaxApp.feature.user.movieBooking.view.BookingScreen
import com.example.cinemaxApp.core.splashScreen.LandingPage
import com.example.cinemaxApp.feature.user.auth.view.choiceSceen
import com.example.cinemaxApp.feature.admin.auth.view.Adminpage
import com.example.cinemaxApp.feature.user.auth.view.UserPage
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

    }
}

