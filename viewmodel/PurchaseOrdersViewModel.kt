// viewmodel/PurchaseOrdersViewModel.kt
package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salesmonitoringsystem.data.PurchaseOrder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PurchaseOrdersViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val purchaseOrdersCollection = db.collection("purchaseOrders")

    private val _purchaseOrders = MutableStateFlow<List<PurchaseOrder>>(emptyList())
    val purchaseOrders: StateFlow<List<PurchaseOrder>> = _purchaseOrders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadPurchaseOrders()
    }

    private fun loadPurchaseOrders() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val snapshot = purchaseOrdersCollection.get().await()
                val ordersList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(PurchaseOrder::class.java)?.copy(id = doc.id)
                }
                _purchaseOrders.value = ordersList
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load purchase orders: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createPurchaseOrder(purchaseOrder: PurchaseOrder) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val docRef = purchaseOrdersCollection.add(purchaseOrder).await()
                val newPurchaseOrder = purchaseOrder.copy(id = docRef.id)
                val currentList = _purchaseOrders.value.toMutableList()
                currentList.add(newPurchaseOrder)
                _purchaseOrders.value = currentList
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to create purchase order: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePurchaseOrder(purchaseOrder: PurchaseOrder) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                purchaseOrdersCollection.document(purchaseOrder.id)
                    .set(purchaseOrder)
                    .await()

                val currentList = _purchaseOrders.value.toMutableList()
                val index = currentList.indexOfFirst { it.id == purchaseOrder.id }
                if (index != -1) {
                    currentList[index] = purchaseOrder
                    _purchaseOrders.value = currentList
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update purchase order: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePurchaseOrder(purchaseOrderId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                purchaseOrdersCollection.document(purchaseOrderId)
                    .delete()
                    .await()

                val currentList = _purchaseOrders.value.toMutableList()
                currentList.removeAll { it.id == purchaseOrderId }
                _purchaseOrders.value = currentList
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete purchase order: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to clear error state
    fun clearError() {
        _error.value = null
    }
}