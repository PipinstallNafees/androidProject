package com.example.cinemaxApp.feature.admin.addMovie.model


data class Movie(
    val id: Int,
    val title: String,
    val date: String,
    var seatsAvailable: Int
)
