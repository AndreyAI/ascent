package com.example.diplomstrava.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.LastActivityFromApp
import com.example.diplomstrava.data.Person

@Database(
    entities = [
        Activity::class,
        Person::class,
        LastActivityFromApp::class
    ], version = DatabaseStrava.DB_VERSION
)
abstract class DatabaseStrava : RoomDatabase() {

    abstract fun activityDao(): ActivityDao
    abstract fun personDao(): PersonDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "strava-database"
    }
}