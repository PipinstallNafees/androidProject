package com.example.cinemaxApp.feature.admin.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService

class AdminLoginViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
): ViewModel() {
    suspend fun login(email: String, password: String): Result<Boolean> {
        val result = authService.signInWithEmail(email, password)

        return if (result.isSuccess) {
            val user = result.getOrNull()
            val uid = user?.uid
            if (uid != null && firestoreService.getUserType(uid) == "admin") {
                Result.success(true)
            } else {
                authService.signOut()
                Result.failure(Exception("Access denied: not a 'admin'"))
            }
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown login error"))
        }
    }
}