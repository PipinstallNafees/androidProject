package com.example.cinemaxApp.feature.user.ticket.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.Attendee
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.model.Seat
import com.example.cinemaxApp.core.model.Ticket
import kotlinx.coroutines.launch

class TicketViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {
    var movie by mutableStateOf<Movie?>(null)
    var ticket by  mutableStateOf<Ticket?>(null)


    fun setData() {
        viewModelScope.launch {
            movie = firestoreService.getOpenMovie()
            ticket = firestoreService.getTicket(authService.getCurrentUser()!!.uid, movie!!.id)
        }
    }
}