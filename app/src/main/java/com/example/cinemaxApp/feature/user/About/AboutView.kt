
package com.example.cinemaxApp.feature.user.About

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.R // Update if your logo is in a different package

@Composable
fun AboutView(nav: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
                )
            )
    ) {
        AboutUsTopBar( { nav.popBackStack() } )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF37383F),
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cinemax_logo_final), // Replace with your logo resource
                        contentDescription = "Cinemax Club Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Cinemax Club",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Cinemax Club is the official movie club of Silicon University, created for students who are passionate about the world of cinema. It serves as a platform for film lovers to explore, appreciate, and discuss movies that go beyond mainstream entertainment. Whether it's classic storytelling or modern experimental styles, our club embraces all forms of cinematic expression.\n\nOur club conducts weekly screenings, where students come together to experience a diverse range of films—from international cinema and indie creations to lesser-known gems that often go unnoticed.\n\nOur club is especially focused on bringing attention to undiscovered movies, offering students a chance to watch and reflect on films that challenge conventional storytelling and highlight unique perspectives from around the world.\n\nBeyond watching films, our club also encourages creativity and critical thinking through various activities like short film-making competition. The short film competitions provide a platform for aspiring filmmakers to bring their ideas to life.\n\nAt its core, our club is a close-knit, inclusive community for anyone who loves movies—whether you're an aspiring filmmaker, a thoughtful critic, or someone who simply enjoys a good film. Cinemax Club is your ticket to discovering new stories, expressing your creativity, and being part of a cinematic journey at Silicon University.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


@Composable
fun AboutUsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 40.dp, bottom = 20.dp)
            .background(Color.Transparent), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "About Us",
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


@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AboutView(nav = rememberNavController())
}