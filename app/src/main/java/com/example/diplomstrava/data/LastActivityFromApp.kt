package com.example.diplomstrava.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = LastActivityFromAppContract.TABLE_NAME
)
data class LastActivityFromApp(
    @PrimaryKey()
    @ColumnInfo(name = LastActivityFromAppContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = LastActivityFromAppContract.Columns.TIME_STAMP)
    val timeStamp: Long
)

