package com.example.salesmonitoringsystem.pages
// pages/SettingsPage.kt


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsPage(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onBackupRequest: () -> Unit
) {
    var showBackupDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        // Theme toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onThemeChange(it) }
            )
        }

        // Backup button
        Button(
            onClick = { showBackupDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Backup Data")
        }
    }

    if (showBackupDialog) {
        AlertDialog(
            onDismissRequest = { showBackupDialog = false },
            title = { Text("Backup Data") },
            text = { Text("Do you want to backup your data to the registered email?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onBackupRequest()
                        showBackupDialog = false
                    }
                ) {
                    Text("Backup")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showBackupDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}