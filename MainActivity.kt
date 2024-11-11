// MainActivity.kt
package com.example.salesmonitoringsystem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.example.salesmonitoringsystem.ui.theme.MyappNavigation
import com.example.salesmonitoringsystem.viewmodel.AuthViewModel
import com.example.salesmonitoringsystem.viewmodel.SalesViewModel
import com.example.salesmonitoringsystem.viewmodel.CustomersViewModel
import com.example.salesmonitoringsystem.viewmodel.ProductsViewModel
import com.example.salesmonitoringsystem.viewmodel.ReportsViewModel
import com.example.salesmonitoringsystem.viewmodel.ExpensesViewModel
import com.example.salesmonitoringsystem.viewmodel.PurchaseOrdersViewModel
import com.example.salesmonitoringsystem.viewmodel.IncomeViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val salesViewModel: SalesViewModel by viewModels()
    private val customersViewModel: CustomersViewModel by viewModels()
    private val productsViewModel: ProductsViewModel by viewModels()
    private val reportsViewModel: ReportsViewModel by viewModels()
    private val expensesViewModel: ExpensesViewModel by viewModels()
    private val purchaseOrdersViewModel: PurchaseOrdersViewModel by viewModels()
    private val incomeViewModel: IncomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            MyApp(darkTheme = isDarkMode) {
                MyappNavigation(
                    authViewModel = authViewModel,
                    salesViewModel = salesViewModel,
                    customersViewModel = customersViewModel,
                    productsViewModel = productsViewModel,
                    reportsViewModel = reportsViewModel,
                    expensesViewModel = expensesViewModel,
                    purchaseOrdersViewModel = purchaseOrdersViewModel,
                    incomeViewModel = incomeViewModel,
                    isDarkMode = isDarkMode,
                    onThemeChange = { isDarkMode = it },
                    onBackupRequest = { performBackup() }
                )
            }
        }
    }

    private fun performBackup() {
        // Implement your backup logic here
        // For example, you could use Firebase Authentication to get the user's email
        // and then use Firebase Storage or your own server to store the backup
        Toast.makeText(this, "Backup initiated", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun MyApp(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            content()
        }
    }
}