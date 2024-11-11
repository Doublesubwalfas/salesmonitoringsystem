package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.salesmonitoringsystem.data.Sale
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SalesViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val _sales = MutableLiveData<List<Sale>>()
    val sales: LiveData<List<Sale>> = _sales

    init {
        // Set up real-time updates
        db.collection("sales")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val salesList = snapshot.documents.map { document ->
                        Sale(
                            id = document.id,
                            customerName = document.getString("customerName") ?: "",
                            productName = document.getString("productName") ?: "",
                            toppings = (document.get("toppings") as? List<String>) ?: emptyList(),
                            extras = (document.get("extras") as? List<String>) ?: emptyList(),
                            amount = document.getDouble("amount") ?: 0.0,
                            timestamp = document.getLong("timestamp") ?: System.currentTimeMillis()
                        )
                    }
                    _sales.value = salesList
                }
            }
    }

    fun addSale(sale: Sale) {
        val saleMap = hashMapOf(
            "customerName" to sale.customerName,
            "productName" to sale.productName,
            "toppings" to sale.toppings,
            "extras" to sale.extras,
            "amount" to sale.amount,
            "timestamp" to sale.timestamp
        )

        db.collection("sales")
            .add(saleMap)
            .addOnFailureListener { e ->
                // Handle error
            }
    }
}