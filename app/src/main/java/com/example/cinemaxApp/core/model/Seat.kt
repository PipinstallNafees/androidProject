package com.example.cinemaxApp.core.model

import com.google.firebase.firestore.PropertyName

data class Seat(
    var label: String = "",
    @get:PropertyName("isBooked")
    @set:PropertyName("isBooked")
    var isBooked: Boolean = false
)
