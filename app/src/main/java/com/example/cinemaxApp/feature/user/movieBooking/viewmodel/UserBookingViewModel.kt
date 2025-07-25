package com.example.cinemaxApp.feature.user.movieBooking.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.model.Attendee
import com.example.cinemaxApp.core.model.Ticket
import kotlinx.coroutines.launch

class UserBookingViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {
    var attendeeList = mutableListOf<Attendee>()
    var movie by mutableStateOf<Movie?>(null)
    var ticket by  mutableStateOf<Ticket?>(null)
    var isBookedState by mutableStateOf<Boolean>(false)

//    init {
//    }

    fun getMovie() {
        viewModelScope.launch {
            movie = firestoreService.getOpenMovie()
            Log.d("MoveInsideVM", movie?.id.toString())
        }
    }

    fun bookAttendee(newAttendeeList: MutableList<Attendee>) {
        viewModelScope.launch {
            val currentMovie = movie
            if (currentMovie == null){
                return@launch
            }
            attendeeList = newAttendeeList
            Log.d("AttendeeList", attendeeList.size.toString())
            val ticketToBook = Ticket(
                uid = authService.getCurrentUser()!!.uid,
                movieId = currentMovie.id,
                attendeeList = attendeeList
            )
            val ticketId = firestoreService.addTicket(ticketToBook)
            Log.d("TicketAdded", ticketId)
            ticket = firestoreService.getTicket(ticketId)
            Log.d("TicketAdded", ticket?.id ?: "")
            firestoreService.updateMovie(currentMovie.copy(bookedSeats = currentMovie.bookedSeats+1))
            Log.d("BookedSeatUpdated", (currentMovie.bookedSeats+1).toString())
            movie = firestoreService.getOpenMovie()
            Log.d("BookedS", movie?.bookedSeats.toString())
        }

        // Add logic to deduct available seats
        // Add logic to create a ticket obj & push ticket

//        selectedMovie?.let { movie ->
//                attendeeList.add(Attendee(name, branch, sic, movie.id))
//                selectedMovie = movie.copy(totalSeats = movie.totalSeats - 1)
//        }
    }

    fun isBooked(movieId: String, uid: String = authService.getCurrentUser()!!.uid) {
        viewModelScope.launch {
            isBookedState = firestoreService.isBooked(movieId, uid)
        }
    }
//    add fun to check if already booked
}
