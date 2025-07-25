package com.example.cinemaxApp.feature.admin.verifyTicket.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject

@Composable
fun TicketResultScreen(ticketJson: String) {
    val ticket = remember {
        JSONObject(ticketJson)
    }

    Column(Modifier.padding(16.dp)) {
        Text("🎫 Ticket Scanned Successfully", fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text("ID: ${ticket.getString("ticket_id")}")
        Text("Name: ${ticket.getString("name")}")
        Text("Event: ${ticket.getString("event")}")
        Text("Date: ${ticket.getString("date")}")
        Text("Seat: ${ticket.getString("seat")}")
    }
}
