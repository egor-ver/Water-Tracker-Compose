package com.example.watertracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WaterDay::class], version = 1)
abstract class WaterDatabase : RoomDatabase() {
    abstract fun waterDao(): WaterDao
}