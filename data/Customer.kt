package com.example.salesmonitoringsystem.data

data class Customer(
    val id: String,
    val name: String,
    val contactNumber: String,
    val email: String? = null,
    val address: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)