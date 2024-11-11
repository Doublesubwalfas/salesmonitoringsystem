package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.viewmodel.AuthState
import com.example.salesmonitoringsystem.viewmodel.AuthViewModel
import com.example.salesmonitoringsystem.viewmodel.SalesViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import com.example.salesmonitoringsystem.components.DrawerContent
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    salesViewModel: SalesViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate("Login")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onNavigate = { route ->
                    navController.navigate(route)
                },
                onSignOut = {
                    authViewModel.signout()
                },
                closeDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sales Monitoring System") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Dashboard", fontSize = 32.sp)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        DashboardButton(
                            icon = Icons.Default.ShoppingCart,
                            text = "Sales",
                            onClick = { navController.navigate("sales") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.Person,
                            text = "Customers",
                            onClick = { navController.navigate("customers") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.Inventory,
                            text = "Products",
                            onClick = { navController.navigate("products") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.Assessment,
                            text = "Reports",
                            onClick = { navController.navigate("reports") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.Receipt,
                            text = "Expense",
                            onClick = { navController.navigate("expense") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.ShoppingBasket,
                            text = "Purchase Orders",
                            onClick = { navController.navigate("purchase_orders") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.History,
                            text = "Activity Log",
                            onClick = { navController.navigate("activity_log") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.Store,
                            text = "Stores",
                            onClick = { navController.navigate("stores") }
                        )
                    }
                    item {
                        DashboardButton(
                            icon = Icons.Default.AttachMoney,
                            text = "Income",
                            onClick = { navController.navigate("income") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}