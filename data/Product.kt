// data/Product.kt
package com.example.salesmonitoringsystem.data

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val stock: Int = 0,
    val category: String = "",
    val timestamp: Long = System.currentTimeMillis()
)