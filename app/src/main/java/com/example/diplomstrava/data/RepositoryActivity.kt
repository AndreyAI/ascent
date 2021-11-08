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
        time: String,
        distance: String,
        description: String
    ) {
        val activity = Activity(
            id = 0L,
            name = name,
            type = type,
            date = date,
            time = 0,
            distance = 71.1,
            elevation = 0,
            description = description
        )
        activityDao.insertActivities(listOf(activity))
    }

    fun queryNewActivities(): List<Activity> {
        val response = Networking.stravaApi.getActivities()
            .execute()//Network.getSearchMovieCall(query, movieTypeToString(type)).execute()
        Timber.d(response.body().toString())
        val activities = response.body() ?: emptyList()
        activityDao.insertActivities(activities)
        return activities
    }

}