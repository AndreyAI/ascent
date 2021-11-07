package com.example.diplomstrava.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.ActivityContract
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM ${ActivityContract.TABLE_NAME}")
    fun getActivities(): Flow<List<Activity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivities(activities: List<Activity>)


}