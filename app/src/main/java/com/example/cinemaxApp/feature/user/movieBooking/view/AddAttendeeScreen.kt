package com.example.cinemaxApp.feature.user.movieBooking.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.cinemaxApp.core.model.Attendee
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel

@Composable
fun AddAttendeeScreen(nav: NavHostController, viewModel: UserBookingViewModel) {
    val movie = viewModel.movie ?: return

    // TODO: Review These codes
//    val currentCount = viewModel.attendeeList.count { it.movieId == movie.id }
//    if (currentCount >= 2) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("⚠️ Booking Limit Reached!", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.error)
//            Spacer(Modifier.height(12.dp))
//            Text("Only 2 persons are allowed to book per movie.", style = MaterialTheme.typography.bodyMedium)
//            Spacer(Modifier.height(24.dp))
//            Button(onClick = { nav.popBackStack("movieDetail", inclusive = false) }) {
//                Text("Back to Movie Details")
//            }
//        }
//        return
//    }

    // Attendee 1
    var name1 by remember { mutableStateOf("") }
    var branch1 by remember { mutableStateOf("") }
    var sic1 by remember { mutableStateOf("") }

    // Ask for 2nd attendee
    var addSecondPerson by remember { mutableStateOf(false) }

    // Attendee 2
    var name2 by remember { mutableStateOf("") }
    var branch2 by remember { mutableStateOf("") }
    var sic2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 25.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {
        AddAttendeeTopBar({ nav.popBackStack() })
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            //        Text("🎟 Enter Attendee Details", style = MaterialTheme.typography.headlineSmall)
            Text("📝 Note: Max 2 persons are allowed per booking", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCF6679))
            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF37383F),
                    contentColor = Color.White
                )
            ) {
                Column (
                    modifier = Modifier.padding(10.dp)
                ) {
                    // Attendee 1
                    Text("👤 Attendee 1", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        name1,
                        { name1 = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 6.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color(0xFFCF6679),
                            focusedIndicatorColor = Color(0xFFCF6679),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedLabelColor = Color(0xFFCF6679),
                            unfocusedLabelColor = Color.Gray,
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(branch1, { branch1 = it }, label = { Text("Branch") }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFCF6679),
                        focusedIndicatorColor = Color(0xFFCF6679),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        focusedLabelColor = Color(0xFFCF6679),
                        unfocusedLabelColor = Color.Gray,
                    ))
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(sic1, { sic1 = it }, label = { Text("SIC ID") }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFCF6679),
                        focusedIndicatorColor = Color(0xFFCF6679),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        focusedLabelColor = Color(0xFFCF6679),
                        unfocusedLabelColor = Color.Gray,
                    ))
                    Spacer(Modifier.height(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF37383F),
                    contentColor = Color.White
                )
            ) {
                Column (
                    modifier = Modifier.padding(10.dp)
                ) {
                    // Ask for Attendee 2

                    Text("➕ Add second attendee?")
                    Spacer(Modifier.height(8.dp))
                    Row {
                        Switch(
                            checked = addSecondPerson,
                            onCheckedChange = { addSecondPerson = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFCF6679),
                                checkedTrackColor = Color(0xFFFFCDD2)
                            )
                        )
                    }

                    // Attendee 2
                    if (addSecondPerson == true) {
                        Spacer(Modifier.height(32.dp))
                        Text("👤 Attendee 2", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(name2, { name2 = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = TextFieldDefaults.colors(
                            cursorColor = Color(0xFFCF6679),
                            focusedIndicatorColor = Color(0xFFCF6679),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedLabelColor = Color(0xFFCF6679),
                            unfocusedLabelColor = Color.Gray,
                        ))
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(branch2, { branch2 = it }, label = { Text("Branch") }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = TextFieldDefaults.colors(
                            cursorColor = Color(0xFFCF6679),
                            focusedIndicatorColor = Color(0xFFCF6679),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedLabelColor = Color(0xFFCF6679),
                            unfocusedLabelColor = Color.Gray,
                        ))
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(sic2, { sic2 = it }, label = { Text("SIC ID") }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = TextFieldDefaults.colors(
                            cursorColor = Color(0xFFCF6679),
                            focusedIndicatorColor = Color(0xFFCF6679),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedLabelColor = Color(0xFFCF6679),
                            unfocusedLabelColor = Color.Gray,
                        ))
                    }
                }
            }
        }

//        Spacer(Modifier.height(32.dp))
        Spacer(modifier = Modifier.weight(1f))

        // Confirm Button
        Button(
            onClick = {
                // Add first attendee
                var attendeeList: MutableList<Attendee> = mutableListOf()
                attendeeList.add(Attendee(name1, branch1, sic1))
                // Add second attendee if chosen and filled
                Log.d("InView", "@78hfy-jk")
                if (addSecondPerson == true && name2.isNotBlank() && branch2.isNotBlank() && sic2.isNotBlank()) {
                    Log.d("InsideIf", "op-yun7g")
                    attendeeList.add(Attendee(name2, branch2, sic2))
                }
                viewModel.addAttendee(attendeeList)

                nav.navigate(Screen.SeatBooking.route)

                // TODO: Review the below code
//                val previousEntry = nav.previousBackStackEntry
//                Log.d("BackStack", "Previous Destination: ${previousEntry?.destination?.route}")

            },
            enabled = name1.isNotBlank() && branch1.isNotBlank() && sic1.isNotBlank(),
//            modifier = Modifier.align(Alignment.End)
            modifier = Modifier
//                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEB5757),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Proceed To Seat booking")
        }
    }
}


@Composable
fun AddAttendeeTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Add Attendee",
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


@Preview
@Composable
fun AddAttendeeScreenPreview() {
    AddAttendeeScreen(rememberNavController(), viewModel())
}