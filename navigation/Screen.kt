package com.example.salesmonitoringsystem.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object Products : Screen("products_route")
    object Sales : Screen("sales_route")
    object Income : Screen("income_route")
    object Expenses : Screen("expenses_route")
    object PurchaseOrders : Screen("purchase_orders_route")
    object Reports : Screen("reports_route")
    object Settings : Screen("settings_route")
    object Customers : Screen("customers_route")

}