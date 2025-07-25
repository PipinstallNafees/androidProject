package com.example.cinemaxApp.core.model

data class Ticket(
    var id: String = "",
    var uid: String = "",
    var movieId: String = "",
    var attendeeList: List<Attendee> = emptyList(),
    var seats: List<String> = emptyList()
)