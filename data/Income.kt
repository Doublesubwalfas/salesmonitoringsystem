package com.example.salesmonitoringsystem.data

data class Income(
    val source: String,
    val description: String,
    val amount: Double,
    val timestamp: Long
)