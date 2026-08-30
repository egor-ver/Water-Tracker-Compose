package com.example.watertracker.data.repository

import android.content.Context
import com.example.watertracker.data.datastore.WaterDataStore
import com.example.watertracker.data.local.WaterDao
import com.example.watertracker.data.local.WaterDatabase
import com.example.watertracker.data.local.WaterDay
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WaterRepositoryImpl @Inject constructor(
    private val dataStore: WaterDataStore,

    private val dao: WaterDao
): WaterRepository{

    override suspend fun saveGoal(amount: Int){
        dataStore.saveGoal(amount)
    }
    override suspend fun saveCurrent(amount: Int){
        dataStore.saveCurrent(amount)
    }
    override suspend fun saveDate(date: String){
        dataStore.saveDate(date)
    }
    override suspend fun getFirstDate(): String{
        return dataStore.getFirstDate()
    }
    override val currentFlow: Flow<Int> = dataStore.currentFlow
    override val goalFlow: Flow<Int> = dataStore.goalFlow
    override val historyFlow: Flow<List<WaterDay>> = dao.getAllDays()
    override suspend fun saveDay(date: String, amount: Int){
        dao.insertDay(WaterDay(date, amount))
    }
}
