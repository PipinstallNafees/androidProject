package com.example.cinemaxApp.feature.admin.profile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.core.model.User
import kotlinx.coroutines.launch


class AdminProfileViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
): ViewModel() {
    var user by mutableStateOf<User?>(null)

    fun getUser() {
        viewModelScope.launch {
            user = firestoreService.getUser(authService.getCurrentUser()!!.uid)!!
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }
}