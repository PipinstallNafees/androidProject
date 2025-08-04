package com.example.cinemaxApp.feature.admin.verifyTicket.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.json.JSONObject

@Preview
@Composable
fun TicketResultScreenPreview() {
    var ticketJson = """{"ticketId":"6h0TyziaSutTEQ3fBfVY","movieName":"marvel","date":"2025-08-10","time":"19:30","seat":["K19"],"AttendeeDet":[{"branch":"ryuii9","name":"dhkko","sic":"fhii"}]}"""
    TicketDetailsScreen(rememberNavController(), ticketJson)
}

@Composable
fun TicketDetailsScreen(nav: NavHostController, ticketJson: String) {
    val ticket = remember {
        JSONObject(ticketJson)
    }

    val backgroundColor = Color(0xFF121212)
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Ticket", "Others")

    val ticketId = ticket.getString("ticketId")
    val movieName = ticket.getString("movieName")
    val date = ticket.getString("date")
    val time = ticket.getString("time")

    val seatArray = ticket.getJSONArray("seat")
    val seats = List(seatArray.length()) { i -> seatArray.getString(i) }
    val attendeeDetArray = ticket.getJSONArray("AttendeeDet")
    val attendees = List(attendeeDetArray.length()) { i ->
        val attendee = attendeeDetArray.getJSONObject(i)
        Triple(
            attendee.getString("branch"),
            attendee.getString("name"),
            attendee.getString("sic")
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 25.dp, vertical = 20.dp)
    ) {
        // Top App Bar (from your previous style)
        TicketDetailsTopBar({nav.popBackStack()})

        // Tab Bar
//        TabRow(
//            selectedTabIndex = selectedTabIndex,
//            containerColor = Color.Transparent,
//            contentColor = Color.White,
//            indicator = { tabPositions ->
//                TabRowDefaults.Indicator(
//                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
//                    color = Color.Cyan
//                )
//            }
//        ) {
//            tabs.forEachIndexed { index, title ->
//                Tab(
//                    selected = selectedTabIndex == index,
//                    onClick = { selectedTabIndex = index },
//                    text = { Text(title, color = if (selectedTabIndex == index) Color.Cyan else Color.LightGray) }
//                )
//            }
//        }
//
        Spacer(modifier = Modifier.height(20.dp))

        // Movie Ticket Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🎬 Movie: $movieName", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("📅 Date: $date", color = Color.LightGray, fontSize = 14.sp)
                Text("⏰ Time: $time", color = Color.LightGray, fontSize = 14.sp)
                Text("📍 Location: Auditorium", color = Color.LightGray, fontSize = 14.sp)
                Text("💺 Seats: ${seats.joinToString(", ")}", color = Color.Cyan, fontSize = 14.sp)
                Text("🎟️ Ticket ID: $ticketId", color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Attendee Cards
        Text("Attendees", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))

        attendees.forEach { (branch, name, sic) ->
            AttendeeCard(branch, name, sic)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun AttendeeCard(branch: String, name: String, sic: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Branch: ${branch}", color = Color.White, fontSize = 14.sp)
            Text("Name: ${name}", color = Color.Cyan, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text("SIC: ${sic}", color = Color.LightGray, fontSize = 14.sp)
        }
    }
}

@Composable
fun TicketDetailsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "E-Ticket",
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