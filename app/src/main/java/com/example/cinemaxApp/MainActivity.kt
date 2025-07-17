package com.example.cinemaxApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.navigation.AppNavigation
import com.example.cinemaxApp.ui.theme.AndroidProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProjectTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

