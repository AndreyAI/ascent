package com.example.diplomstrava.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.ActivityContract
import com.example.diplomstrava.data.LastActivityFromApp
import com.example.diplomstrava.data.LastActivityFromAppContract
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM ${ActivityContract.TABLE_NAME} ORDER BY ${ActivityContract.Columns.ID} DESC")
    fun getActivities(): List<Activity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivities(activities: List<Activity>)

    @Query("DELETE FROM ${ActivityContract.TABLE_NAME}")
    fun deleteActivities()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastActivityFromApp(lastActivityFromApp: LastActivityFromApp)

    @Query("SELECT * FROM ${LastActivityFromAppContract.TABLE_NAME}")
    fun getLastActivityFromApp(): List<LastActivityFromApp>

}