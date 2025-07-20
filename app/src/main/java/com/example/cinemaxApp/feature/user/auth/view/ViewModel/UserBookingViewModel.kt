package com.example.cinemaxApp.feature.user.auth.view.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinemaxApp.feature.admin.addMovie.model.Movie
import com.example.cinemaxApp.feature.user.auth.view.Model.Attendee

class UserBookingViewModel : ViewModel() {
    var selectedMovie by mutableStateOf<Movie?>(null)
    val attendeeList = mutableStateListOf<Attendee>()

    fun bookAttendee(name: String, branch: String, sic: String) {
        selectedMovie?.let { movie ->
            val alreadyBooked = attendeeList.count { it.movieId == movie.id }

            if (alreadyBooked < 2 && movie.totalSeats > 0) {
                attendeeList.add(Attendee(name, branch, sic, movie.id))
                selectedMovie = movie.copy(totalSeats = movie.totalSeats - 1)
            }
        }
    }
}
