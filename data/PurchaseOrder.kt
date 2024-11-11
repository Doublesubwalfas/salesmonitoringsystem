
package com.example.salesmonitoringsystem.data

data class PurchaseOrder(
    val id: String = "",
    val orderNumber: String = "",
    val supplierName: String = "",
    val orderDate: String = "",
    val items: List<PurchaseOrderItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val status: String = "",
    val paymentStatus: String = "",
    val deliveryDate: String = "",
    val notes: String = ""
)

data class PurchaseOrderItem(
    val productId: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val unitPrice: Double = 0.0,
    val totalPrice: Double = 0.0
)