package com.example.cinemaxApp.feature.admin.addMovie.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.firebase.FirestoreService
import kotlinx.coroutines.launch

// MovieAdminViewModel.kt
open class MovieAdminViewModel(
    private val firestoreService: FirestoreService
) : ViewModel() {
    var movieList by mutableStateOf(listOf<Movie>())

//    init {
//    }

    fun getMovieList() {
        viewModelScope.launch {
            movieList = firestoreService.getAllMovies()
            Log.d("MovieList", movieList.size.toString())
        }
    }

    fun addMovie(title: String, desc: String, seats: Int, posterUrl: String = "", date: String, time: String, genre: String,rating: String, director: String) {
        viewModelScope.launch {
            val movie = Movie(
                title = title,
                description = desc,
                posterUrl = posterUrl,
                totalSeats = seats,
                bookedSeats = 0,
                date = date,
                time = time,
                genre = genre.split(",").map { it.trim()},
                rating = rating.toFloatOrNull(),
                director = director,
            )
            val movieId = firestoreService.addMovie(movie)
            movieList = movieList + movie.copy(id = movieId)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            movieList = movieList.filter { it.id != movie.id }
            firestoreService.deleteMovie(movie)
        }
    }

    open fun updateMovie(updated: Movie) {
        viewModelScope.launch {
            Log.d("ID", updated.id)

            movieList = movieList.map {
                if (it.id == updated.id) updated else it
            }
            firestoreService.updateMovie(updated)
        }
    }

    open fun getMovieById(id: String): Movie? = movieList.find { it.id == id }

    fun allocateSeat(movieId: String, seatNo: Int) {
//        movieList = movieList.map {
//            if (it.id == movieId && seatNo <= it.totalSeats && !it.allocatedSeats.contains(seatNo)) {
//                it.copy(allocatedSeats = it.allocatedSeats + seatNo)
//            } else it
//        }
    }

}
