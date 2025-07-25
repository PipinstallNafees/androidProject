package com.example.cinemaxApp

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.navigation.AppNavigation
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.core.splashScreen.SplashScreen
import com.google.rpc.context.AttributeContext.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
//        val splashScreen = installSplashScreen()
//        splashScreen.setKeepOnScreenCondition { false }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            splashScreen.setKeepOnScreenCondition { false }
//        }
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            splashScreen.setOnExitAnimationListener { splash ->
//                val iconView = splash.iconView
//                iconView.animate()
//                    .scaleX(0f)
//                    .scaleY(0f)
//                    .alpha(0f)
//                    .setDuration(500L)
//                    .setInterpolator(AccelerateDecelerateInterpolator())
//                    .withEndAction {
//                        splash.remove()
//                    }
//                    .start()
//            }
//        }

        setContent {
            enableEdgeToEdge()

            var showSplash by remember { mutableStateOf(true) }
            val authService = remember { AuthService() }
            val firestoreService = remember { FirestoreService() }
            val navController = rememberNavController()

            var startScreen by remember { mutableStateOf<String?>(null) }

            // Splash delay
            LaunchedEffect(Unit) {
                delay(2000)
                showSplash = false
            }

            // Once splash is gone, resolve startScreen
//            LaunchedEffect(showSplash) {
            LaunchedEffect(Unit) {
//                if (!showSplash) {
                    startScreen = if (authService.isUserLoggedIn()) {
                        val userType = firestoreService.getUserType(authService.getCurrentUser()!!.uid)
                        if (userType == "admin") Screen.AdminDashboard.route else Screen.UserDashboard.route
                    } else {
                        Screen.UserTypeSelection.route
                    }
//                }
            }

            // UI
            when {
                showSplash -> SplashScreen()

                startScreen != null -> {
                    AppNavigation(
                        navHostController = navController,
                        startScreen = startScreen!!,
                        authService = authService,
                        firestoreService = firestoreService
                    )
                }

                else -> {
                    // Optional: show loading indicator while determining startScreen
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Color(0xFF201E1E)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF8D2D2D),
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}




