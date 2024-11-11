package com.example.salesmonitoringsystem.data

import java.time.LocalDate

data class Report(
    val id: String,
    val type: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val data: Map<String, Any>
)