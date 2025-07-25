package com.example.cinemaxApp.feature.admin.auth.view

// 🔽 Compose & AndroidX Imports
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.auth.viewmodel.AdminLoginViewModel
import kotlinx.coroutines.launch


@Composable
fun AdminLoginScreen(nav: NavHostController, viewModel: AdminLoginViewModel) {
    val carbonBlack = Color(0xFF1A1919)
    val mahroon = Color(0xFF710C0C)
    val white = Color.White
    val context = LocalContext.current
    val adminId = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var coroutineScope = rememberCoroutineScope()

    // TODO: Try to remove the Emojis, Degrades the premium look & feel
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(carbonBlack)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎬 Welcome to Cinemax Admin Panel",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = mahroon,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .padding(12.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .background(white, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "👋 Hello Admin",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = carbonBlack
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Divider(thickness = 1.dp, color = mahroon)

                Text(
                    text = "Enter your Admin ID",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = carbonBlack
                    )
                )

                OutlinedTextField(
                    value = adminId.value,
                    onValueChange = { adminId.value = it },
                    label = {
                        Text("Admin ID", fontFamily = FontFamily.Monospace)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Text(
                    text = "Enter your Password",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = carbonBlack
                    )
                )

                OutlinedTextField(
                    value =password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text("Password", fontFamily = FontFamily.Monospace)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Button(
                    onClick = {
                        // TODO: Add Validation Logics [Silicon Email validation, Email Validation]
                        // TODO: Review the below commented code [Hardcoded Login Logic] & remove if not needed in future implementation
                        // Handle login logic here
//                        if (adminId.value=="cinemax" && password.value=="cinemax") {
//                            // Navigate to admin dashboard or perform login action
//                            nav.navigate("adminDashboard")
//                        } else {
//                            Toast.makeText(context, "Invalid Admin ID or Password", Toast.LENGTH_SHORT).show()
//                        }

                        coroutineScope.launch {
                            var result = viewModel.login(adminId.value, password.value)
                            if (result.isSuccess) {
                                // Navigate to Admin Dashboard & empty the nav stack
                                nav.navigate(Screen.AdminDashboard.route) {
                                    popUpTo(Screen.UserTypeSelection.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            } else {
                                val error = result.exceptionOrNull()?.message ?: "Login failed, Invalid Admin ID or Password"
                                // TODO: Change the view of toast (looks small on screen & may get unnoticed)
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mahroon
                    )
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Monospace,
                            color = white
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminpagePreview() {
    AdminLoginScreen(nav = rememberNavController(), viewModel = viewModel())
}
