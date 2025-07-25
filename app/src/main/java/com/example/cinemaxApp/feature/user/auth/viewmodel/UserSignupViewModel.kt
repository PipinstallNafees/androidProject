package com.example.cinemaxApp.feature.user.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.User

class UserSignupViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
): ViewModel() {
    suspend fun signUp(email: String, password: String, name: String, sic: String): Result<Boolean> {
        return try {
            val result = authService.signUpWithEmail(email, password)
            val user = result.getOrNull()

            if (user != null) {
                Log.d("UserSignupViewModel", user.email.toString())
                firestoreService.addUser(
                    User(
                    uid = user.uid,
                    name = name,
                    email = email,
                    sic = sic,
                    role = "user"
                )
                )
                Result.success(true)  // ✅ Return success as Boolean
            } else {
                Result.failure(Exception("User creation failed"))
            }
        } catch (e: Exception) {
            Log.d("UserSignupView", e.toString())
            Result.failure(e)
        }
    }
}