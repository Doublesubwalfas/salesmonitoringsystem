package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.components.DrawerContent
import com.example.salesmonitoringsystem.data.PurchaseOrder
import com.example.salesmonitoringsystem.data.PurchaseOrderItem
import com.example.salesmonitoringsystem.viewmodel.PurchaseOrdersViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseOrdersPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    purchaseOrdersViewModel: PurchaseOrdersViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State variables for purchase order details
    var supplierName by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onNavigate = { route -> navController.navigate(route) },
                onSignOut = { /* Handle sign out */ },
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Purchase Orders") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Supplier Name
                OutlinedTextField(
                    value = supplierName,
                    onValueChange = { supplierName = it },
                    label = { Text("Supplier Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Total Amount
                OutlinedTextField(
                    value = totalAmount,
                    onValueChange = { totalAmount = it },
                    label = { Text("Total Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Product Name
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Quantity
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Unit Price
                OutlinedTextField(
                    value = unitPrice,
                    onValueChange = { unitPrice = it },
                    label = { Text("Unit Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                        val purchaseOrderItem = PurchaseOrderItem(
                            productName = productName,
                            quantity = quantity.toIntOrNull() ?: 0,
                            unitPrice = unitPrice.toDoubleOrNull() ?: 0.0,
                            totalPrice = (quantity.toIntOrNull() ?: 0) * (unitPrice.toDoubleOrNull() ?: 0.0)
                        )

                        val purchaseOrder = PurchaseOrder(
                            supplierName = supplierName,
                            orderDate = currentDate,
                            totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                            items = listOf(purchaseOrderItem),
                            status = "Pending"
                        )

                        purchaseOrdersViewModel.createPurchaseOrder(purchaseOrder)

                        // Reset fields
                        supplierName = ""
                        totalAmount = ""
                        productName = ""
                        quantity = ""
                        unitPrice = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Purchase Order")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display purchase orders
                val purchaseOrders by purchaseOrdersViewModel.purchaseOrders.collectAsState()
                purchaseOrders.forEach { order ->
                    Text("Supplier: ${order.supplierName}, Total Amount: $${order.totalAmount}, Date: ${order.orderDate}")
                }
            }
        }
    }
}