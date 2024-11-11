package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

// viewmodel/AppViewModelProvider.kt
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AuthViewModel()
        }
        initializer {
            SalesViewModel()
        }
        // Add other ViewModels
    }
}