package com.example.cinemaxApp.feature.user.movieBooking.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.model.Attendee
import com.example.cinemaxApp.core.model.Seat
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
    var seatMap = mutableStateOf<Map<String, Seat>>(emptyMap())

//    init {
//    }

    fun getMovie() {
        viewModelScope.launch {
            movie = firestoreService.getOpenMovie()
            Log.d("MoveInsideVM", movie?.id.toString())
        }
    }

    fun addAttendee(newAttendeeList: MutableList<Attendee>) {
        viewModelScope.launch {
            attendeeList = newAttendeeList
        }
    }

    fun bookTicket(selectedSeats: MutableState<Set<String>>) {
        viewModelScope.launch {
            val currentMovie = movie
            if (currentMovie == null){
                return@launch
            }

            setSeatsStatus(selectedSeats)

            val ticketToBook = Ticket(
                uid = authService.getCurrentUser()!!.uid,
                movieId = currentMovie.id,
                attendeeList = attendeeList,
                seats = selectedSeats.value.toList()
            )
            val ticketId = firestoreService.addTicket(ticketToBook)
            Log.d("TicketAdded", ticketId)
            ticket = firestoreService.getTicket(ticketId)
            Log.d("TicketAdded", ticket?.id ?: "")
            firestoreService.updateMovie(currentMovie.copy(bookedSeats = currentMovie.bookedSeats+attendeeList.size))
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

    fun getAllSeatData() {
        Log.d("GetAllSeat0", "Inside the function")
        viewModelScope.launch {
            try {
                firestoreService.getSeats(movie!!.id, seatMap)
                Log.d("GetAllSeat", seatMap.toString())
            } catch (e: Exception) {
                Log.d("GetAllSeat", e.toString())
            }
        }
    }

    fun getOpenMovie() {
        viewModelScope.launch {
            movie = firestoreService.getOpenMovie()
            Log.d("OpenMovie", movie?.id ?: "No Movie Found")
        }
    }

    fun setSeatsStatus(selectedSeats: MutableState<Set<String>>) {
        viewModelScope.launch {
            selectedSeats.value.forEach { seat ->
                firestoreService.setSeatStatus(seat, movie!!.id)
            }
        }
    }
}
