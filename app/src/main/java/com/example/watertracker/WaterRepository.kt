package com.example.watertracker

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "water_prefs")

class WaterRepository(private val context: Context){
    private val currentKey = intPreferencesKey("current")
    private val goalKey = intPreferencesKey("goal")

    private val dateKey = stringPreferencesKey("date")
    val currentFlow: Flow<Int> = context.dataStore.data
        .map{prefs -> prefs[currentKey]?: 0}
    val goalFlow: Flow<Int> = context.dataStore.data
        .map{prefs -> prefs[goalKey]?: 2000}
    val dateFlow: Flow<String> = context.dataStore.data
        .map{prefs -> (prefs[dateKey]?: "")}
    suspend fun saveCurrent(value: Int){
        context.dataStore.edit{prefs -> prefs[currentKey] = value}
    }
    suspend fun saveGoal(value: Int){
        context.dataStore.edit{prefs -> prefs[goalKey] = value}
    }
    suspend fun saveDate(value: String){
        context.dataStore.edit{prefs -> prefs[dateKey] = value}
    }
}
