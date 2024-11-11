// viewmodel/ProductsViewModel.kt
package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salesmonitoringsystem.data.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val snapshot = productsCollection
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .await()

                val productsList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Product::class.java)?.copy(id = doc.id)
                }

                _products.value = productsList
            } catch (e: Exception) {
                _error.value = "Failed to load products: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val productData = hashMapOf(
                    "name" to product.name,
                    "price" to product.price,
                    "description" to product.description,
                    "stock" to product.stock,
                    "category" to product.category,
                    "timestamp" to System.currentTimeMillis()
                )

                productsCollection.add(productData).await()
                loadProducts()
            } catch (e: Exception) {
                _error.value = "Failed to add product: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                productsCollection.document(product.id).update(
                    mapOf(
                        "name" to product.name,
                        "price" to product.price,
                        "description" to product.description,
                        "stock" to product.stock,
                        "category" to product.category
                    )
                ).await()

                loadProducts()
            } catch (e: Exception) {
                _error.value = "Failed to update product: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                productsCollection.document(productId).delete().await()
                loadProducts()
            } catch (e: Exception) {
                _error.value = "Failed to delete product: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}