package com.example.cinemaxApp.feature.user.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService

class UserSignupViewModelFactory(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserSignupViewModel(authService, firestoreService) as T
    }
}