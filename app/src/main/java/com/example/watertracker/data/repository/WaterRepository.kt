package com.example.watertracker.data.repository

import android.content.Context
import com.example.watertracker.data.datastore.WaterDataStore
import com.example.watertracker.data.local.WaterDatabase
import com.example.watertracker.data.local.WaterDay
import kotlinx.coroutines.flow.Flow


class WaterRepository(private val dataStore: WaterDataStore, private val context: Context){

    suspend fun saveGoal(amount: Int){
        dataStore.saveGoal(amount)
    }
    suspend fun saveCurrent(amount: Int){
        dataStore.saveCurrent(amount)
    }
    suspend fun saveDate(date: String){
        dataStore.saveDate(date)
    }
    suspend fun getFirstDate(): String{
        return dataStore.getFirstDate()
    }
    val currentFlow: Flow<Int> = dataStore.currentFlow
    val goalFlow: Flow<Int> = dataStore.goalFlow
    private val dao = WaterDatabase.getDatabase(context).waterDao()
    val historyFlow: Flow<List<WaterDay>> = dao.getAllDays()
    suspend fun saveDay(date: String, amount: Int){
        dao.insertDay(WaterDay(date, amount))
    }
}
