package com.example.cinemaxApp.feature.user.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import kotlinx.coroutines.launch

class UserLoginViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
): ViewModel() {
    suspend fun login(email: String, password: String): Result<Boolean> {
        val result = authService.signInWithEmail(email, password)

        return if (result.isSuccess) {
            val user = result.getOrNull()
            val uid = user?.uid
            Log.d("UserLoginViewModel", user?.email.toString())
            if (uid != null && firestoreService.getUserType(uid) == "user") {
                Result.success(true)
            } else {
                authService.signOut()
                Result.failure(Exception("Access denied: not a 'user'"))
            }
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown login error"))
        }
    }
}