package com.example.watertracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_history")
data class WaterDay(
    @PrimaryKey val date: String,
    val amount: Int
)