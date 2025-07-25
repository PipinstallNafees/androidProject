package com.example.cinemaxApp.feature.admin.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.feature.user.dashboard.viewmodel.UserDashboardViewModel

class AdminDashboardViewModelFactory(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdminDashboardViewModel(authService, firestoreService) as T
    }
}