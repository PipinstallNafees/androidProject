package com.example.cinemaxApp.core.model

import com.google.firebase.firestore.PropertyName

data class Movie(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var posterUrl: String = "",
    var totalSeats: Int = 0,
    var bookedSeats: Int = 0,
    var director: String= "",
    var rating: Float? = null,
    var genre: List<String>? = null,
//    var allocatedSeats: List<Int> = emptyList(),
    var date: String = "",
    var time: String = "",
    @get:PropertyName("isActive")
    @set:PropertyName("isActive")
    var isActive: Boolean = true
)


