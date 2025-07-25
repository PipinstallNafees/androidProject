package com.example.cinemaxApp.core.firebase


import android.util.Log
import com.example.cinemaxApp.core.model.Movie
import com.example.cinemaxApp.core.model.User
import com.example.cinemaxApp.core.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await


class FirestoreService {

    // Fetching the FirebaseFirestore obj reference
    private val db = FirebaseFirestore.getInstance()

    // Firestore collection references
    private val usersCollection = db.collection("users")
    private val moviesCollection = db.collection("movies")
    private val ticketsCollection = db.collection("tickets")



    // Movie Related fun
    suspend fun addMovie(movie: Movie): String {
        val docRef = moviesCollection.document()
        docRef.set(movie.copy(id = docRef.id)).await()
        return docRef.id
    }

    suspend fun updateMovie(movie: Movie) {
        moviesCollection.document(movie.id).set(movie).await()
    }

    suspend fun getMovie(movieId: String): Movie? {
        // No current use case
        // Added for future use
        val movieSnapshot = moviesCollection.document(movieId).get().await()
        return if (movieSnapshot.exists()) movieSnapshot.toObject<Movie>() else null
    }

    suspend fun getOpenMovie(): Movie? {
        val movieSnapshot = moviesCollection
            .whereEqualTo("isActive",true)
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        try {
            return movieSnapshot.documents.firstOrNull()?.toObject(Movie::class.java)
        } catch(e: Exception) {
            Log.d("FirestoreOpenMovieException", e.toString())
            return null
        }
//         TODO: Check out this format below & use the optimised one
//        return try {
//            movieSnapshot.documents.firstOrNull()?.toObject(Movie::class.java)
//        } catch(e: Exception) {
//            Log.d("Open Movie Exception", e.toString())
//            null
//        }
    }

    suspend fun getAllMovies(): List<Movie> {
        val movieSnapshot = moviesCollection.get().await()
        return try {
            movieSnapshot.documents.mapNotNull { it.toObject(Movie::class.java) }
        } catch(e: Exception) {
            Log.d("FirestoreGetAllMovieException", e.toString())
            emptyList<Movie>()
        }
    }



    // Ticket Related fun
    suspend fun addTicket(ticket: Ticket): String {
        val docRef = ticketsCollection.document()
        docRef.set(ticket.copy(id = docRef.id)).await()
        return docRef.id
    }

    suspend fun getTicket(uid: String, movieId: String): Ticket? {
//        TODO: Check if tere are any use case for this
        val ticketSnapshot = ticketsCollection.whereEqualTo("uid",uid)
            .whereEqualTo("movieId", movieId)
            .get()
            .await()
        return try {
            ticketSnapshot.documents.firstOrNull()?.toObject(Ticket::class.java)
        } catch(e: Exception) {
            null
        }
    }

    suspend fun getTicket(ticketId: String): Ticket? {
        val ticketSnapshot = ticketsCollection.document(ticketId).get().await()
        return if (ticketSnapshot.exists()) ticketSnapshot.toObject<Ticket>() else null
    }

    suspend fun isBooked(movieId: String, uid: String): Boolean {
        val result = ticketsCollection
            .whereEqualTo("movieId", movieId)
            .whereEqualTo("uid", uid)
            .get()
            .await()

        return !result.isEmpty
    }



    // User Related fun
    suspend fun getUserType(uid: String): String {
        return usersCollection.document(uid).get().await().toObject<User>()?.role ?: ""
    }

    suspend fun addUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    suspend fun getUser(uid: String): User? {
        val snapshot = usersCollection.document(uid).get().await()
        return if (snapshot.exists()) snapshot.toObject<User>() else null
    }

    suspend fun updateUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    suspend fun deleteUser(uid: String) {
        usersCollection.document(uid).delete().await()
    }
}
