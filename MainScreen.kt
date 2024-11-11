package com.example.salesmonitoringsystem

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.salesmonitoringsystem.navigation.SetupNavGraph
import com.example.salesmonitoringsystem.viewmodel.AuthViewModel
import com.example.salesmonitoringsystem.viewmodel.ProductsViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Create ViewModels
    val authViewModel: AuthViewModel = viewModel()
    val productsViewModel: ProductsViewModel = viewModel()

    // Setup Navigation Graph with ViewModels
    SetupNavGraph(
        navController = navController,
        authViewModel = authViewModel,
        productsViewModel = productsViewModel
    )
}