package com.example.salesmonitoringsystem.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salesmonitoringsystem.components.DrawerContent
import com.example.salesmonitoringsystem.utils.DateUtils
import kotlinx.coroutines.launch

data class ActivityLog(val action: String, val timestamp: Long)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLogPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val activityLogs = remember {
        listOf(
            ActivityLog("User login", System.currentTimeMillis() - 3600000),
            ActivityLog("New sale added", System.currentTimeMillis() - 7200000),
            ActivityLog("Product updated", System.currentTimeMillis() - 86400000)
        )
    }

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
                    title = { Text("Activity Log") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
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
                items(activityLogs) { log ->
                    ActivityLogItem(log)
                }
            }
        }
    }
}

@Composable
fun ActivityLogItem(log: ActivityLog) {
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
            Text(text = log.action)
            Text(text = "Time: ${DateUtils.formatActivityLogDate(log.timestamp)}")
        }
    }
}