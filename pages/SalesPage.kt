package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.components.DrawerContent
import com.example.salesmonitoringsystem.data.Sale
import com.example.salesmonitoringsystem.viewmodel.AuthState
import com.example.salesmonitoringsystem.viewmodel.AuthViewModel
import com.example.salesmonitoringsystem.viewmodel.SalesViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    salesViewModel: SalesViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val authState = authViewModel.authState.observeAsState()
    val sales by salesViewModel.sales.observeAsState(emptyList())

    var customerName by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var filterText by remember { mutableStateOf("") }
    var sortOrder by remember { mutableStateOf("newest") }
    var showSortMenu by remember { mutableStateOf(false) }

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate("login")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onNavigate = { route -> navController.navigate(route) },
                onSignOut = { authViewModel.signout() },
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sales") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(Icons.Default.Sort, contentDescription = "Sort")
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
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val sale = Sale(
                            customerName = customerName,
                            productName = productName,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            timestamp = System.currentTimeMillis()
                        )
                        salesViewModel.addSale(sale)
                        customerName = ""
                        productName = ""
                        amount = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Sale")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    label = { Text("Search sales") },
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn {
                    items(sales.filter {
                        it.customerName.contains(filterText, ignoreCase = true) ||
                                it.productName.contains(filterText, ignoreCase = true)
                    }.sortedWith(when (sortOrder) {
                        "oldest" -> compareBy { it.timestamp }
                        "amount_desc" -> compareByDescending { it.amount }
                        else -> compareByDescending { it.timestamp }
                    })) { sale ->
                        SaleItem(sale)
                    }
                }
            }
        }
    }

    if (showSortMenu) {
        DropdownMenu(
            expanded =  showSortMenu,
            onDismissRequest = { showSortMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("Newest First") },
                onClick = { sortOrder = "newest"; showSortMenu = false }
            )
            DropdownMenuItem(
                text = { Text("Oldest First") },
                onClick = { sortOrder = "oldest"; showSortMenu = false }
            )
            DropdownMenuItem(
                text = { Text("Amount: High to Low") },
                onClick = { sortOrder = "amount_desc"; showSortMenu = false }
            )
        }
    }
}

@Composable
fun SaleItem(sale: Sale) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Customer: ${sale.customerName}")
            Text(text = "Product: ${sale.productName}")
            Text(text = "Amount: $${sale.amount}")
            Text(text = "Date: ${formatDate(sale.timestamp)}")
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}