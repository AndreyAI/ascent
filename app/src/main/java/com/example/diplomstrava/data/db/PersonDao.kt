package com.example.diplomstrava.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomstrava.data.Person
import com.example.diplomstrava.data.PersonContract
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM ${PersonContract.TABLE_NAME}")
    fun getPerson(): Flow<Person?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

}