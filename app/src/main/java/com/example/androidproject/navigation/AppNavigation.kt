package com.example.androidproject.navigation


import Adminpage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidproject.screens.BookingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "admin") {
        composable("admin") {
            Adminpage(navController)
        }
        composable("booking") {
            BookingScreen()
        }

    }
}

