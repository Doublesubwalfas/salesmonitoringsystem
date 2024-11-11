package com.example.salesmonitoringsystem.viewmodel

import androidx.lifecycle.ViewModel
import com.example.salesmonitoringsystem.data.Income
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class IncomeViewModel : ViewModel() {
    private val _incomeState = MutableStateFlow(IncomeState())
    val incomeState: StateFlow<IncomeState> = _incomeState

    fun addIncome(income: Income) {
        _incomeState.update { currentState ->
            val updatedIncomes = currentState.incomes + income
            currentState.copy(
                incomes = updatedIncomes,
                totalIncome = updatedIncomes.sumOf { it.amount }
            )
        }
    }

    fun deleteIncome(income: Income) {
        _incomeState.update { currentState ->
            val updatedIncomes = currentState.incomes - income
            currentState.copy(
                incomes = updatedIncomes,
                totalIncome = updatedIncomes.sumOf { it.amount }
            )
        }
    }

    fun getIncomeForPeriod(startDate: Long, endDate: Long): List<Income> {
        return _incomeState.value.incomes.filter { it.timestamp in startDate..endDate }
    }
}

data class IncomeState(
    val incomes: List<Income> = emptyList(),
    val totalIncome: Double = 0.0
)