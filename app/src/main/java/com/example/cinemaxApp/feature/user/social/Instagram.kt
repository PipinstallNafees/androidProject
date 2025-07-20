package com.example.cinemaxApp.feature.user.social

import android.R.attr.id
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.R


@Composable
fun Instagram(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background color to white
    ) {
        Spacer(modifier = Modifier.padding(16.dp)) // Optional spacer for top padding
        IconButton(
            onClick = {
                navController.popBackStack()// Navigate back to the previous screen
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start) // Align icon to the start
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Logout",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        // Center Content Vertically and Horizontally
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Optional padding around the screen
            contentAlignment = Alignment.Center // Center content in the Box
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Follow Us on Instagram",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // QR Code Image
                Image(
                    painter = painterResource(id = R.drawable.cinemaxinsta), // Replace with your image resource ID
                    contentDescription = "QR Code for Instagram",
                    modifier = Modifier
                        .size(200.dp) // Adjust size if necessary
                        .padding(bottom = 16.dp)
                )

                // Instagram Link
                Text(
                    text = "Visit our Instagram page",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            ("https://www.instagram.com/sitcine" + "max?utm_source=qr&igsh=cjlzaTgyd2VmZW1j").toUri()
                        )
                        context.startActivity(intent) // Redirect to the Instagram page
                    }
                )
            }
        }
    }
}

