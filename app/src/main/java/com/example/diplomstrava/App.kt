package com.example.diplomstrava

import android.app.Application
import android.app.NotificationChannel
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.diplomstrava.presentation.NotificationChanel
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        NotificationChanel.create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            //.setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }
}