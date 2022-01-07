package com.example.diplomstrava.data

import android.content.Context
import androidx.work.*
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import com.example.diplomstrava.presentation.addactivity.PostActivityWorker
import com.example.diplomstrava.utils.ResponseBodyException
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepositoryActivities @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: StravaApi,
    private val activityDao: ActivityDao,
    private val personDao: PersonDao
) {

    fun saveActivity(
        name: String,
        type: String,
        date: String,
        time: String,
        distance: Double,
        description: String
    ) {
        Timber.d(date)
        val activity = Activity(
            id = 0L,
            name = name,
            type = type,
            date = date,
            time = time.toLong(),
            distance = distance,
            elevation = 0,
            description = description
        )
        try {
            activityDao.insertActivities(listOf(activity))
            api.postActivity(activity).execute() ?: throw(ResponseBodyException())
            activityDao.insertLastActivityFromApp(
                LastActivityFromApp(id = 1L, Calendar.getInstance().timeInMillis)
            )
        } catch (t: Throwable) {
            addWorkerTask(activity)
        }
    }

    private fun addWorkerTask(activity: Activity) {
        Timber.d("create worker")

        val workData = workDataOf(
            ActivityContract.Columns.NAME to activity.name,
            ActivityContract.Columns.TYPE to activity.type,
            ActivityContract.Columns.DATE to activity.date,
            ActivityContract.Columns.TIME to activity.time,
            ActivityContract.Columns.DISTANCE to activity.distance,
            ActivityContract.Columns.ELEVATION to activity.elevation,
            ActivityContract.Columns.DESCRIPTION to activity.description
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PostActivityWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest)
    }

    fun queryNewActivities(): List<PersonWithActivity> {
        val responseActivities = api.getActivities().execute() ?: throw(ResponseBodyException())
        val activities = responseActivities.body() ?: throw(ResponseBodyException())
        activityDao.insertActivities(activities)
        val responsePerson = api.getPersonData().execute() ?: throw(ResponseBodyException())
        val person = responsePerson.body() ?: throw(ResponseBodyException())
        personDao.insertPerson(person)
        return generatePersonWithActivity(person, activities)
    }

    fun queryCachedActivities(): List<PersonWithActivity> {
        val activities = activityDao.getActivities()//.asReversed()
        val person = personDao.getPerson()
        return generatePersonWithActivity(person, activities)
    }

    private fun generatePersonWithActivity(
        person: Person,
        activities: List<Activity>
    ): List<PersonWithActivity> {
        val list = mutableListOf<PersonWithActivity>()
        activities.forEach {
            val personWithActivity = PersonWithActivity(
                firstName = person.firstName,
                lastName = person.lastName,
                avatarUrl = person.avatarUrl,
                activity = it
            )
            list.add(personWithActivity)
        }
        return list
    }
    companion object {
        const val DOWNLOAD_WORK_ID = "download work"
    }
}