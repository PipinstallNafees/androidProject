package com.example.cinemaxApp.feature.admin.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import kotlinx.coroutines.launch

class AdminDashboardViewModel(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
): ViewModel() {
    var userName by mutableStateOf("")

    fun getUserName() {
        viewModelScope.launch {
            userName = firestoreService.getUser(authService.getCurrentUser()!!.uid)!!.name.trim().split("\\s+".toRegex())[0]
        }
    }
}