package com.example.salesmonitoringsystem.data

import com.google.firebase.firestore.FirebaseFirestore

// data/Repository.kt
class Repository(private val db: FirebaseFirestore) {
    suspend fun addProduct(product: Product) {
        db.collection("products").add(product)
    }

    suspend fun addSale(sale: Sale) {
        db.collection("sales").add(sale)
    }

    // Add other database operations
}