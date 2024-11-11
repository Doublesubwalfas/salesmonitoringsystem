package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.components.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorePage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var stores by remember { mutableStateOf(listOf<Store>()) }
    var showAddDialog by remember { mutableStateOf(false) }

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
                    title = { Text("Stores") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showAddDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Store")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(stores) { store ->
                    StoreItem(
                        store = store,
                        onDelete = { stores = stores.filter { it != store } }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddStoreDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newStore ->
                stores = stores + newStore
                showAddDialog = false
            }
        )
    }
}

@Composable
fun StoreItem(store: Store, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Name: ${store.name}")
                Text(text = "Address: ${store.location.address}")
                Text(text = "Manager: ${store.manager.name}")
                Text(text = "Phone: ${store.manager.phoneNumber}")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun AddStoreDialog(onDismiss: () -> Unit, onAdd: (Store) -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var managerName by remember { mutableStateOf("") }
    var managerPhone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Store") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Store Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = managerName,
                    onValueChange = { managerName = it },
                    label = { Text("Manager Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = managerPhone,
                    onValueChange = { managerPhone = it },
                    label = { Text("Manager Phone") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val newStore = Store(
                    id = "",  // You might want to generate a unique ID here
                    name = name,
                    location = Location(address = address),
                    manager = Manager(name = managerName, phoneNumber = managerPhone)
                )
                onAdd(newStore)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Make sure these data classes match the ones you defined earlier
data class Store(
    val id: String,
    val name: String,
    val location: Location,
    val manager: Manager
)

data class Location(
    val address: String,
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val country: String = ""
)

data class Manager(
    val name: String,
    val phoneNumber: String,
    val email: String = ""
)