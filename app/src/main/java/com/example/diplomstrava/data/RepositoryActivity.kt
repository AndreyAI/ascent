package com.example.diplomstrava.data

import android.content.Context
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.networking.StravaApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryActivity @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: StravaApi,
    private val activityDao: ActivityDao
) {

   // private val activityDao = Database.instance.activityDao()

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
        val response = api.getActivities().execute()
        val activities = response.body() ?: emptyList()
        activityDao.insertActivities(activities)
        return activities
    }

}