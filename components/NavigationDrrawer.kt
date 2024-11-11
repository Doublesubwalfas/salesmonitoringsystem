package com.example.salesmonitoringsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salesmonitoringsystem.navigation.Screen

@Composable
fun DrawerContent(
    onNavigate: (String) -> Unit,
    onSignOut: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))

        // Home navigation
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = false,
            onClick = {
                onNavigate(Screen.Home.route)
                closeDrawer()
            }
        )

        // Dashboard navigation
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") },
            selected = false,
            onClick = {
                onNavigate(Screen.Dashboard.route)
                closeDrawer()
            }
        )

        // Profile navigation
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = {
                onNavigate(Screen.Profile.route)
                closeDrawer()
            }
        )

        // Sign Out
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
            label = { Text("Sign Out") },
            selected = false,
            onClick = {
                onSignOut()
                closeDrawer()
            }
        )
    }
}