package com.example.watertracker.data.datastore

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "water_prefs")
class WaterDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val currentKey = intPreferencesKey("current")
    private val goalKey = intPreferencesKey("goal")
    private val dateKey = stringPreferencesKey("date")

    val currentFlow: Flow<Int> = context.dataStore.data.map{
        preferences -> preferences[currentKey] ?: 0
    }
    val goalFlow: Flow<Int> = context.dataStore.data.map {
        preferences -> preferences[goalKey] ?: 2000
    }
    val dateFlow: Flow<String> = context.dataStore.data.map {
        preferences -> preferences[dateKey] ?: ""
    }
    suspend fun saveCurrent(amount: Int){
        context.dataStore.edit{
            preferences -> preferences[currentKey] = amount
        }
    }
    suspend fun saveGoal(amount: Int){
        context.dataStore.edit {
            preferences -> preferences[goalKey] = amount
        }
    }
    suspend fun saveDate(date: String){
        context.dataStore.edit {
            preferences -> preferences[dateKey] = date
        }
    }
    suspend fun getFirstDate(): String {
        return dateFlow.first()
    }
}