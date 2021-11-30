package com.example.diplomstrava.data

import android.content.Context
import androidx.work.WorkManager
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import com.example.diplomstrava.utils.ResponseBodyException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class RepositoryPerson @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: StravaApi,
    private val personDao: PersonDao,
    private val activityDao: ActivityDao
) {
    fun getPersonFlow(): Flow<Person?> {
        return personDao.getPersonFlow()
    }

    fun queryPersonData(): Person? {

        val response = api.getPersonData().execute()
        val person = response.body()
        //?: throw(ResponseBodyException())//emptyList()//throw(ResponseBodyException())
        if (person != null) {
            personDao.insertPerson(person)
        }
        return person

    }

    fun updateWeight(weight: Double) {

        val person = personDao.getPerson()

        val personUpdated = Person(
            id = person.id,
            lastUpdate = person.lastUpdate,
            firstName = person.firstName,
            lastName = person.lastName,
            country = person.country,
            sex = person.sex,
            avatarUrl = person.avatarUrl,
            followingCount = person.followingCount,
            followersCount = person.followersCount,
            weight = weight
        )
        personDao.insertPerson(personUpdated)
        api.updatePerson(personUpdated).execute()
        Timber.d("person update")
    }

    fun logout() {
        personDao.deletePerson()
        activityDao.deleteActivities()
        WorkManager.getInstance(context).cancelUniqueWork(RepositoryActivity.DOWNLOAD_WORK_ID)
        api.logout("https://www.strava.com/oauth/deauthorize").execute()
    }


}