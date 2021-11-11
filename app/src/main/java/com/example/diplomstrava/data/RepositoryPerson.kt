package com.example.diplomstrava.data

import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import com.example.diplomstrava.utils.ResponseBodyException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryPerson @Inject constructor(
    private val api: StravaApi,
    private val personDao: PersonDao
) {
    fun getPerson(): Flow<Person?> {
        return personDao.getPerson()
    }

    fun queryPersonData(): Person {
        val response = api.getPersonData().execute()
        val person = response.body() ?: throw(ResponseBodyException())//emptyList()//throw(ResponseBodyException())
        personDao.insertPerson(person)
        return person
    }
}