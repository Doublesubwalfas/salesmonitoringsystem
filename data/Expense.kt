// File: app/src/main/java/com/example/salesmonitoringsystem/data/Expense.kt

package com.example.salesmonitoringsystem.data

data class Expense(
    val id: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val date: Long = System.currentTimeMillis()
)