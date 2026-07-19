package com.example.watertracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WaterViewModel: ViewModel() {
    var current by mutableIntStateOf(0)
        private set
    var goal by mutableIntStateOf(2000)
        private set
    var lastAmount by mutableIntStateOf(0)
        private set
    fun addWater(amount: Int){
        current += amount
        lastAmount = amount
    }
    fun updateGoal(amount: Int?){
        if(amount != null && amount > 0) {
            goal = amount
        }
    }
    fun deleteLast(){
        current = (current - lastAmount).coerceAtLeast(0)
        lastAmount = 0
    }
    fun trackProgress(): Float{
        return(current.toFloat() / goal.toFloat()).coerceIn(0f, 1f)
    }
}
