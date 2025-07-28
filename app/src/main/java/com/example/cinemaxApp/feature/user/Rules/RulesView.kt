package com.example.cinemaxApp.feature.user.Guidelines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun RulesView(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Guidelines for SIT Cinemax",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    val guidelines = listOf(
                        "1. You must come with your ID card.",
                        "2. Maintain decorum inside the Cinemax.",
                        "3. Don’t carry bags, umbrellas, food items, or water bottles into the Cinemax.",
                        "4. Sit according to your allotted seat number. Don’t sit in someone else’s seat.",
                        "5. Don’t argue with anyone. If you have any issue, contact a Cinemax member.",
                        "6. Arrive exactly at the reporting time. Late entry is allowed only within 10 minutes of screening.",
                        "7. Don’t come without confirmation of your seat.",
                        "8. Check your seat condition before sitting. If it's damaged, inform a Cinemax member. Damaged seats after screening may result in a penalty."
                    )

                    guidelines.forEach { rule ->
                        Text(
                            text = rule,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Regards,\nSIT Cinemax",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuidelinesViewPreview() {
    RulesView(nav = rememberNavController())
}
