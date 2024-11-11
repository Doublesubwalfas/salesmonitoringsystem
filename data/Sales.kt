package com.example.salesmonitoringsystem.data

data class Sale(
    val id: String = "",
    val customerName: String = "",
    val productName: String = "",
    val toppings: List<String> = emptyList(),
    val extras: List<String> = emptyList(),
    val amount: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)