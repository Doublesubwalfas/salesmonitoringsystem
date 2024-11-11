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
import com.example.salesmonitoringsystem.data.Expense
import com.example.salesmonitoringsystem.viewmodel.ExpensesViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    expensesViewModel: ExpensesViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }

    // Observe expenses from ViewModel
    val expenses by expensesViewModel.expenses.observeAsState(initial = emptyList())

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
                    title = { Text("Expenses") },
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
                OutlinedTextField(
                    value = expenseName,
                    onValueChange = { expenseName = it },
                    label = { Text("Expense Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = expenseAmount,
                    onValueChange = { expenseAmount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val amount = expenseAmount.toDoubleOrNull() ?: 0.0
                        val expense = Expense(
                            description = expenseName,
                            amount = amount,
                            category = "", // Add category if needed
                            date = System.currentTimeMillis()
                        )
                        expensesViewModel.addExpense(expense)
                        expenseName = ""
                        expenseAmount = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Expense")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display expenses in a LazyColumn
                LazyColumn {
                    items(expenses) { expense ->
                        ExpenseItem(expense = expense)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = expense.description)
            Text(text = "Amount: $${expense.amount}")
        }
    }
}