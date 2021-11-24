package com.example.diplomstrava.data

import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import com.example.diplomstrava.utils.ResponseBodyException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RepositoryActivity @Inject constructor(
    private val api: StravaApi,
    private val activityDao: ActivityDao,
    private val personDao: PersonDao
) {

    fun getActivities(): List<Activity> {
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
            //personId = 8098,
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
        val activities = activityDao.getActivities()
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

}