package com.example.cinemaxApp.feature.admin.addMovie.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String = "",
    val totalSeats: Int,
    val allocatedSeats: List<Int> = emptyList(),
    val posterUrl: String = "",
    val date: String = "",
    val time: String = ""
)


