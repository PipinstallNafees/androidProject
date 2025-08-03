package com.example.cinemaxApp.feature.user.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.profile.viewmodel.UserProfileViewModel

@Preview
@Composable
fun Preview() {
    UserProfileScreen(rememberNavController(), viewModel())
}

@Composable
fun UserProfileScreen(nav: NavHostController, viewModel: UserProfileViewModel) {
    val user = viewModel.user

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 25.dp, vertical = 40.dp)
    ) {
        ProfileTopBar({ nav.popBackStack() })

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                ProfileInfoRow("🎬 Name", user?.name)
                Spacer(Modifier.height(8.dp))
                ProfileInfoRow("📜 SIC", user?.sic)
                Spacer(Modifier.height(8.dp))
                ProfileInfoRow("📧 Email", user?.email)
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.signOut()
                nav.navigate(Screen.UserTypeSelection.route) {
                    popUpTo(Screen.UserDashboard.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCF6679),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

    }
}

@Composable
fun ProfileInfoRow(label: String, value: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            color = Color(0xFFBBBBBB),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value ?: "Not Available",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ProfileTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        // Left-aligned Back Icon
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}