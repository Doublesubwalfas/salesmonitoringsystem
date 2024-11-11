package com.example.salesmonitoringsystem.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// components/CommonComponents.kt
@Composable
fun LoadingSpinner() {
    CircularProgressIndicator()
}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error
    )
}