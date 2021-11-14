package com.example.diplomstrava.data

import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import com.example.diplomstrava.utils.ResponseBodyException
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class RepositoryPerson @Inject constructor(
    private val api: StravaApi,
    private val personDao: PersonDao
) {
    fun getPersonFlow(): Flow<Person?> {
        return personDao.getPersonFlow()
    }

    fun queryPersonData(): Person {
        val response = api.getPersonData().execute()
        val person = response.body()
            ?: throw(ResponseBodyException())//emptyList()//throw(ResponseBodyException())
        personDao.insertPerson(person)
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
        Timber.d("person update")

    }

}