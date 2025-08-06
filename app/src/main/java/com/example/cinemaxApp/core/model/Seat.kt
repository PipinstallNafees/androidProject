package com.example.cinemaxApp.core.model

import com.google.firebase.firestore.PropertyName

// Learn about @get:PropertyName("isBooked")
//    @set:PropertyName("isBooked")
// Firestore need a no arg contractor to create a data class obj
// For that need to set default val for each data member in the data class

data class Seat(
    var label: String = "",
    @get:PropertyName("isBooked")
    @set:PropertyName("isBooked")
    var isBooked: Boolean = false,
    var movieId: String = ""
)
