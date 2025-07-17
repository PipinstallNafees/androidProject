package com.example.cinemaxApp.data


data class Movie(
    val id: Int,
    val title: String,
    val date: String,
    var seatsAvailable: Int
)
