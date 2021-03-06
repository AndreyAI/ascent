package com.example.diplomstrava.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomstrava.data.Person
import com.example.diplomstrava.data.PersonContract
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface PersonDao {
    @Query("SELECT * FROM ${PersonContract.TABLE_NAME}")
    fun getPersonFlow(): Flow<Person?>

    @Query("SELECT * FROM ${PersonContract.TABLE_NAME}")
    fun getPerson(): Person

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Query("DELETE FROM ${PersonContract.TABLE_NAME}")
    fun deletePerson()

}