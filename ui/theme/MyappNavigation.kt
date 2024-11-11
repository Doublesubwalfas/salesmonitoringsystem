package com.example.salesmonitoringsystem.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salesmonitoringsystem.pages.*
import com.example.salesmonitoringsystem.viewmodel.*

@Composable
fun MyappNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    salesViewModel: SalesViewModel,
    customersViewModel: CustomersViewModel,
    productsViewModel: ProductsViewModel,
    reportsViewModel: ReportsViewModel,
    expensesViewModel: ExpensesViewModel,
    purchaseOrdersViewModel: PurchaseOrdersViewModel,
    incomeViewModel: IncomeViewModel,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onBackupRequest: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginPage(
                modifier = modifier,
                navController = navController, authViewModel = authViewModel
            )
        }
        composable("Signup") {
            SignupPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("Home") {
            HomePage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel,
                salesViewModel = salesViewModel
            )
        }
        composable("sales") {
            SalesPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel,
                salesViewModel = salesViewModel
            )
        }
        composable("customers") {
            CustomersPage(
                modifier = modifier,
                navController = navController,
                customersViewModel = customersViewModel
            )
        }
        composable("products") {
            ProductsPage(
                modifier = modifier,
                navController = navController,
                productsViewModel = productsViewModel
            )
        }
        composable("reports") {
            ReportsPage(
                modifier = modifier,
                navController = navController,
                reportsViewModel = reportsViewModel
            )
        }
        composable("expense") {
            ExpensesPage(
                modifier = modifier,
                navController = navController,
                expensesViewModel = expensesViewModel
            )
        }
        composable("purchase_orders") {
            PurchaseOrdersPage(
                modifier = modifier,
                navController = navController,
                purchaseOrdersViewModel = purchaseOrdersViewModel
            )
        }
        composable("activity_log") {
            ActivityLogPage(
                modifier = modifier,
                navController = navController
            )
        }
        composable("stores") {
            StorePage(
                modifier = modifier,
                navController = navController
            )
        }
        composable("income") {
            IncomePage(
                modifier = modifier,
                navController = navController,
                incomeViewModel = incomeViewModel
            )
        }
        composable("settings") {
            SettingsPage(
                navController = navController,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange,
                onBackupRequest = onBackupRequest
            )
        }
        composable("dashboard") {
            DashboardPage(
                modifier = modifier,
                navController = navController
            )
        }
        composable("profile") {
            ProfilePage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}