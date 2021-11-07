package com.example.diplomstrava.data.db

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: DatabaseStrava
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            DatabaseStrava::class.java,
            DatabaseStrava.DB_NAME
        )
            //.addMigrations(MIGRATION_1_2)
            .build()
    }
}