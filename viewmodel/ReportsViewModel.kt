// viewmodel/ReportsViewModel.kt
package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salesmonitoringsystem.data.Report
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ReportsViewModel : ViewModel() {
    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun generateReport(startDate: LocalDate, endDate: LocalDate, reportType: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Add your report generation logic here
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun exportReport(reportId: String, format: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Add your export logic here
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}