package com.example.watertracker.data.repository

import com.example.watertracker.data.local.WaterDay
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    suspend fun saveGoal(amount: Int)
    suspend fun saveCurrent(amount: Int)
    suspend fun saveDate(date: String)
    suspend fun getFirstDate(): String
    val currentFlow: Flow<Int>
    val goalFlow: Flow<Int>
    val historyFlow: Flow<List<WaterDay>>
    suspend fun saveDay(date: String, amount: Int)
}