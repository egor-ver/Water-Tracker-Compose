package com.example.watertracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: WaterDay)
    @Query("SELECT * FROM water_history ORDER BY date DESC")
    fun getAllDays(): Flow<List<WaterDay>>
}