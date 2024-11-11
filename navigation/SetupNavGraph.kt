package com.example.salesmonitoringsystem.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salesmonitoringsystem.pages.*
import com.example.salesmonitoringsystem.viewmodel.*

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productsViewModel: ProductsViewModel,
    incomeViewModel: IncomeViewModel = viewModel(),
    expensesViewModel: ExpensesViewModel = viewModel(),
    salesViewModel: SalesViewModel = viewModel(),
    reportsViewModel: ReportsViewModel = viewModel(),
    purchaseOrdersViewModel: PurchaseOrdersViewModel = viewModel(),
    customersViewModel: CustomersViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    )  {
        composable(route = Screen.Home.route) {
            HomePage(
                navController = navController,
                authViewModel = authViewModel,
                salesViewModel = salesViewModel
            )
        }

        // New Dashboard Route
        composable(route = Screen.Dashboard.route) {
            DashboardPage(
                navController = navController
            )
        }

        // New Profile Route
        composable(route = Screen.Profile.route) {
            ProfilePage(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.Products.route) {
            ProductsPage(
                navController = navController,
                productsViewModel = productsViewModel
            )
        }

        composable(route = Screen.Sales.route) {
            SalesPage(
                navController = navController,
                authViewModel = authViewModel,
                salesViewModel = salesViewModel
            )
        }

        composable(route = Screen.Income.route) {
            IncomePage(
                navController = navController,
                incomeViewModel = incomeViewModel
            )
        }

        composable(route = Screen.Expenses.route) {
            ExpensesPage(
                navController = navController,
                expensesViewModel = expensesViewModel
            )
        }

        composable(route = Screen.PurchaseOrders.route) {
            PurchaseOrdersPage(
                navController = navController,
                purchaseOrdersViewModel = purchaseOrdersViewModel
            )
        }

        composable(route = Screen.Reports.route) {
            ReportsPage(
                navController = navController,
                reportsViewModel = reportsViewModel
            )
        }

        composable(route = Screen.Customers.route) {
            CustomersPage(
                navController = navController,
                customersViewModel = customersViewModel
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsPage(
                navController = navController,
                isDarkMode = false,
                onThemeChange = {},
                onBackupRequest = {}
            )
        }
    }
}