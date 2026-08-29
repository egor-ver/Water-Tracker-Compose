package com.example.watertracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.datastore.WaterDataStore
import com.example.watertracker.data.repository.WaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class WaterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository = WaterRepository(context = application, dataStore = WaterDataStore(context = application))

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
    fun trackProgress(): Float{
        return if (waterUiState.value.goal > 0) (waterUiState.value.current.toFloat() / waterUiState.value.goal.toFloat()).coerceIn(0f, 1f)
        else 0f
    }




}