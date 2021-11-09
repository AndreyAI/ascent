package com.example.diplomstrava

import android.app.Application
import com.example.diplomstrava.data.db.Database
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        Database.init(this)
    }
}