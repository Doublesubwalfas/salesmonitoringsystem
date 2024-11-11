// File: app/src/main/java/com/example/salesmonitoringsystem/viewmodel/ExpensesViewModel.kt

package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salesmonitoringsystem.data.Expense
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExpensesViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val expensesCollection = db.collection("expenses")

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val snapshot = expensesCollection
                    .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .await()

                val expensesList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Expense::class.java)?.copy(id = doc.id)
                }

                _expenses.value = expensesList
            } catch (e: Exception) {
                _error.value = "Failed to load expenses: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val expenseData = hashMapOf(
                    "amount" to expense.amount,
                    "description" to expense.description,
                    "category" to expense.category,
                    "date" to expense.date
                )

                expensesCollection.add(expenseData).await()
                loadExpenses()
            } catch (e: Exception) {
                _error.value = "Failed to add expense: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                expensesCollection.document(expense.id).update(
                    mapOf(
                        "amount" to expense.amount,
                        "description" to expense.description,
                        "category" to expense.category,
                        "date" to expense.date
                    )
                ).await()

                loadExpenses()
            } catch (e: Exception) {
                _error.value = "Failed to update expense: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                expensesCollection.document(expenseId).delete().await()
                loadExpenses()
            } catch (e: Exception) {
                _error.value = "Failed to delete expense: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
