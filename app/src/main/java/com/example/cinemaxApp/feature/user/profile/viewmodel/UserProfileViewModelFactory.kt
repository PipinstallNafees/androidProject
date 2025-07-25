package com.example.cinemaxApp.feature.user.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserSignupViewModel

class UserProfileViewModelFactory(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(authService, firestoreService) as T
    }
}