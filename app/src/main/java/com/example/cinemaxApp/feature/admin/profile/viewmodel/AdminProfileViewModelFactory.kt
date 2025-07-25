package com.example.cinemaxApp.feature.admin.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.feature.admin.profile.viewmodel.AdminProfileViewModel

class AdminProfileViewModelFactory(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdminProfileViewModel(authService, firestoreService) as T
    }
}