package com.example.diplomstrava.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import androidx.work.WorkManager
import com.example.diplomstrava.data.db.ActivityDao
import com.example.diplomstrava.data.db.PersonDao
import com.example.diplomstrava.networking.StravaApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Long.parseLong
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

    suspend fun getContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)

        do {
            val name = cursor.getString(nameIndex).orEmpty()
            val id = cursor.getLong(idIndex)

            list.add(
                Contact(
                    id = id,
                    name = name,
                    avatarUri = getAvatarForContact(id),
                    phones = getPhonesForContact(id)
                )
            )
        } while (cursor.moveToNext())

        return list
    }

    private fun getPhonesForContact(contactId: Long): String {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): String {
        if (cursor.moveToFirst().not()) return ""
        val list = mutableListOf<String>()
        var phones = ""
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(number)
            phones += number + "\n"
        } while (cursor.moveToNext())
        return phones
    }

    private fun getAvatarForContact(contactId: Long): String {
        return context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            Timber.d(it.toString())
            getAvatarFromCursor(it, contactId)
        }.orEmpty()
    }

    private fun getAvatarFromCursor(cursor: Cursor, contactId: Long): String {
        Timber.d(cursor.moveToFirst().not().toString())
        if (cursor.moveToFirst().not()) return ""
        val contact =
            ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,
                parseLong(contactId.toString())
            )
        val uri = Uri.withAppendedPath(contact, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
            .toString()
        Timber.d(uri)
        return uri
    }


}