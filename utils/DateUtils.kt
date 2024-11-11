// utils/DateUtils.kt
package com.example.salesmonitoringsystem.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatActivityLogDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}