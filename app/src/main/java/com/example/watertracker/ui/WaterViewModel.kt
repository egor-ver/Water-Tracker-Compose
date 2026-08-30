package com.example.watertracker.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    private val repository: WaterRepository
): ViewModel(){

    private val _waterUiState = MutableStateFlow(WaterUiState(0, 2000, 0))
    val waterUiState: StateFlow<WaterUiState> = _waterUiState
    val history = repository.historyFlow
    val today = LocalDate.now().toString()
    init {
        viewModelScope.launch {
            val savedDate = repository.getFirstDate()
            if(today != savedDate){
                repository.saveCurrent(0)
                repository.saveDate(today)
            }
        }
        viewModelScope.launch {
            repository.goalFlow.collect { value -> _waterUiState.value = _waterUiState.value.copy(goal = value)}
        }
        viewModelScope.launch {
            repository.currentFlow.collect { value -> _waterUiState.value = _waterUiState.value.copy(current = value) }
        }

    }
    fun addWater(amount: Int){
        _waterUiState.value = _waterUiState.value.copy(current = waterUiState.value.current + amount, lastAmount = amount)
        viewModelScope.launch {
            repository.saveCurrent(waterUiState.value.current)
            repository.saveDay(today, waterUiState.value.current)
        }

    }
    fun updateGoal(amount: Int){
        _waterUiState.value = _waterUiState.value.copy(goal = amount)
        viewModelScope.launch { repository.saveGoal(amount) }
    }
    fun deleteLast(){
        _waterUiState.value = _waterUiState.value.copy(current = (waterUiState.value.current - waterUiState.value.lastAmount).coerceAtLeast(0))
        viewModelScope.launch { repository.saveCurrent(waterUiState.value.current) }
    }
}