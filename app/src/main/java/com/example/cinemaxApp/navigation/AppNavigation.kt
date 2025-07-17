package com.example.cinemaxApp.navigation


import Adminpage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaxApp.screens.BookingScreen
import com.example.cinemaxApp.screens.LandingPage
import com.example.cinemaxApp.screens.choiceSceen

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

