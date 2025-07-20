package com.example.cinemaxApp.feature.admin.addMovie.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinemaxApp.feature.admin.addMovie.model.Movie

// AdminViewModel.kt
class AdminViewModel : ViewModel() {
    private var nextId = 1
    var movieList by mutableStateOf(listOf<Movie>())
        private set

    fun addMovie(title: String, desc: String, seats: Int, posterUrl: String = "") {
        val movie = Movie(
            id = nextId++,
            title = title,
            description = desc,
            totalSeats = seats,
            posterUrl = posterUrl
        )
        movieList = movieList + movie
    }


    fun updateMovie(updated: Movie) {
        movieList = movieList.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun getMovieById(id: Int): Movie? = movieList.find { it.id == id }

    fun allocateSeat(movieId: Int, seatNo: Int) {
        movieList = movieList.map {
            if (it.id == movieId && seatNo <= it.totalSeats && !it.allocatedSeats.contains(seatNo)) {
                it.copy(allocatedSeats = it.allocatedSeats + seatNo)
            } else it
        }
    }

}
