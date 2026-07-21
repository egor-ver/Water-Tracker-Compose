package com.example.watertracker

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class WaterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository = WaterRepository(application)

    var current by mutableIntStateOf(0)
        private set
    var goal by mutableIntStateOf(2000)
        private set
    var lastAmount by mutableIntStateOf(0)
        private set

    init {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            val savedDate = repository.dateFlow.first()
            if(today != savedDate){
                repository.saveCurrent(0)
                repository.saveDate(today)
            }
        }
        viewModelScope.launch {
            repository.currentFlow.collect { saved ->
                current = saved
            }
        }
        viewModelScope.launch {
            repository.goalFlow.collect { saved ->
                goal = saved
            }
        }
    }

    fun addWater(amount: Int) {
        current += amount
        lastAmount = amount
        viewModelScope.launch {
            repository.saveCurrent(current)
        }
    }

    fun updateGoal(amount: Int?) {
        if (amount != null && amount > 0) {
            goal = amount
            viewModelScope.launch {
                repository.saveGoal(goal)
            }
        }
    }

    fun deleteLast() {
        current = (current - lastAmount).coerceAtLeast(0)
        lastAmount = 0
        viewModelScope.launch {
            repository.saveCurrent(current)
        }
    }

    fun trackProgress(): Float {
        return (current.toFloat() / goal.toFloat()).coerceIn(0f, 1f)
    }
}
