package com.example.cinemaxApp.feature.admin.addMovie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemaxApp.feature.admin.addMovie.ViewModel.AdminViewModel

@Composable
fun CreateMovieScreen(nav: NavController, vm: AdminViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf("") }
    var posterUrl by remember { mutableStateOf("") }
    val isFormValid = title.isNotBlank() && seats.toIntOrNull() != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF23232F))
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "➕ Create New Movie",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("🎬 Movie Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("📝 Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = seats,
                    onValueChange = { seats = it },
                    label = { Text("🪑 Total Seats") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = posterUrl,
                    onValueChange = { posterUrl = it },
                    label = { Text("🖼 Poster URL (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (isFormValid) {
                            vm.addMovie(title, desc, seats.toInt(), posterUrl)
                            nav.navigate("Adminbooking")
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C)),
                    enabled = isFormValid
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Create",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
