
package com.example.cinemaxApp.feature.admin.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.R
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.dashboard.viewmodel.AdminDashboardViewModel

@Composable
fun AdminDashboardScreen(nav: NavHostController, viewModel: AdminDashboardViewModel) {
    var userName by remember { mutableStateOf("") }

    userName = viewModel.userName

    LaunchedEffect(Unit) {
        viewModel.getUserName()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
                )
            )
    ) {
        // Header Image
        Spacer(modifier = Modifier.padding(35.dp) )
        Image(
            // TODO: Remove the below code if 'R.drawable.cinemax_logo_final' will be final
//            painter = painterResource(id = R.drawable.logo),
            painter = painterResource(id = R.drawable.cinemax_logo_final),
            contentDescription = "Cinemax Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        )

        // Welcome Texts
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
            Spacer(modifier = Modifier.padding(top =16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome to",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    ),
                    color = Color.White
                )
                IconButton(
                    onClick = { nav.navigate(Screen.AdminProfile.route) }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cinemax App",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                ),
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Admin [$userName]",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Gray
            )
        }

        // Center the dashboard cards vertically
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .padding(16.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DashboardCard("AdminBooking", R.drawable.booking, Modifier.weight(1f),nav, Screen.AdminMovieList.route)
                            DashboardCard("E-Ticket", R.drawable.ticket, Modifier.weight(1f),nav, Screen.VerifyTicket.route)
                            DashboardCard("About Us", R.drawable.user, Modifier.weight(1f),nav,
                                Screen.AboutUS.route)
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DashboardCard("Instagram", R.drawable.developer, Modifier.weight(1f),nav, Screen.SocialHandle.route)
                            DashboardCard("Rules", R.drawable.user, Modifier.weight(1f),nav, Screen.Rules.route)
                            DashboardCard("Developer", R.drawable.developer, Modifier.weight(1f),nav)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, imageId: Int, modifier: Modifier = Modifier,nav: NavHostController, screen: String = Screen.AdminProfile.route) {
    Card(
        onClick = {
            nav.navigate(screen)
                  },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = title,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    AdminDashboardScreen(nav = rememberNavController(), viewModel = viewModel())
}