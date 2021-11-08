package com.example.diplomstrava.data

import android.net.Network
import com.example.diplomstrava.data.db.Database
import com.example.diplomstrava.networking.Networking
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class RepositoryActivity {

    private val activityDao = Database.instance.activityDao()

    fun getActivities(): Flow<List<Activity>> {
        return activityDao.getActivities()
    }

    fun saveActivity(
        name: String,
        type: String,
        date: String,
        time: Long,
        distance: Double,
        description: String
    ) {
        val activity = Activity(
            id = 0L,
            name = name,
            type = type,
            date = date,
            time = time,
            distance = distance,
            elevation = 0,
            description = description
        )
        activityDao.insertActivities(listOf(activity))
    }

    fun queryNewActivities(): List<Activity> {
        val response = Networking.stravaApi.getActivities().execute()
        val activities = response.body() ?: emptyList()
        activityDao.insertActivities(activities)
        return activities
    }

}