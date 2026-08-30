package com.example.watertracker.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterDatabaseModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WaterDatabase{
        return Room.databaseBuilder(
            context,
            WaterDatabase::class.java,
            "water_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDao(database: WaterDatabase): WaterDao {
        return database.waterDao()
    }

}