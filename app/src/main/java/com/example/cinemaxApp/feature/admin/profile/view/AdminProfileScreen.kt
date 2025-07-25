package com.example.cinemaxApp.feature.admin.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.profile.viewmodel.AdminProfileViewModel

@Composable
fun AdminProfileScreen(nav: NavHostController, viewModel: AdminProfileViewModel) {
    val user = viewModel.user

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🎟 Profile", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("🎬 Name: ${user?.name}")
                Text("📜 SIC: ${user?.sic}")
                Text("📅 email: ${user?.email}")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signOut()
                // Navigate to UserTypeSelection Screen and empty the nav stack
                nav.navigate(Screen.UserTypeSelection.route) {
                    popUpTo(Screen.AdminDashboard.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
        ) {
            Text("Sign Out")
        }

    }
}