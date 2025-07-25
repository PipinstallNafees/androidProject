package com.example.cinemaxApp.feature.admin.addMovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemaxApp.core.firebase.FirestoreService

class MovieAdminViewModelFactory(
    private val firestoreService: FirestoreService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieAdminViewModel(firestoreService) as T
    }
}