// pages/ProductsPage.kt
package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.components.DrawerContent
import com.example.salesmonitoringsystem.components.ProductDialog
import com.example.salesmonitoringsystem.components.ProductItem
import com.example.salesmonitoringsystem.data.Product
import com.example.salesmonitoringsystem.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    productsViewModel: ProductsViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val products by productsViewModel.products.observeAsState(initial = emptyList())
    val loading by productsViewModel.loading.observeAsState(initial = false)
    val error by productsViewModel.error.observeAsState()

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
                    title = { Text("Products") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showAddDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Product")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onEdit = { selectedProduct = product },
                            onDelete = { productsViewModel.deleteProduct(product.id) }
                        )
                    }
                }

                // Error handling
                error?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        // Show snackbar or toast with error message
                        productsViewModel.clearError()
                    }
                }
            }
        }
    }

    // Add/Edit Product Dialog
    if (showAddDialog || selectedProduct != null) {
        ProductDialog(
            product = selectedProduct,
            onDismiss = {
                showAddDialog = false
                selectedProduct = null
            },
            onConfirm = { product ->
                if (selectedProduct != null) {
                    productsViewModel.updateProduct(product) } else {
                    productsViewModel.addProduct(product)
                }
                showAddDialog = false
                selectedProduct = null
            }
        )
    }
}