package com.example.diplomstrava.di

import android.content.Context
import androidx.room.Room
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.data.db.DatabaseStrava
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DatabaseStrava {
        return Room.databaseBuilder(
            context,
            DatabaseStrava::class.java,
            DatabaseStrava.DB_NAME
        )
            //.addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideActivityDao(databaseStrava: DatabaseStrava): ActivityDao {
        return databaseStrava.activityDao()
    }
}