// components/ProductDialog.kt
package com.example.salesmonitoringsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salesmonitoringsystem.data.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (product == null) "Add Product" else "Edit Product") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") })
                TextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val productData = Product(
                    id = product?.id ?: "",
                    name = name,
                    price = price.toDoubleOrNull() ?: 0.0,
                    description = description,
                    stock = stock.toIntOrNull() ?: 0,
                    category = category
                )
                onConfirm(productData)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}