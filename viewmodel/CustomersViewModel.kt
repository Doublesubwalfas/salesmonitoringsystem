package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salesmonitoringsystem.data.Customer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomersViewModel : ViewModel() {
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customersState: StateFlow<List<Customer>> = _customers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // Initial load of customers or setup
        fetchCustomers()
    }

    private fun fetchCustomers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Implement your data fetching logic here
                // For example, from Firebase or local database
                // _customers.value = fetchedCustomers
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val currentList = _customers.value.toMutableList()
                currentList.add(customer)
                _customers.value = currentList
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val currentList = _customers.value.toMutableList()
                val index = currentList.indexOfFirst { it.id == customer.id }
                if (index != -1) {
                    currentList[index] = customer
                    _customers.value = currentList
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCustomer(customerId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val currentList = _customers.value.toMutableList()
                currentList.removeAll { it.id == customerId }
                _customers.value = currentList
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}