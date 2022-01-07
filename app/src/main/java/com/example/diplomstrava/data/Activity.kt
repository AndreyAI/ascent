package com.example.diplomstrava.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(
    tableName = ActivityContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class Activity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActivityContract.Columns.ID)
    @Json(name = ActivityContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ActivityContract.Columns.NAME)
    @Json(name = ActivityContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = ActivityContract.Columns.DISTANCE)
    @Json(name = ActivityContract.Columns.DISTANCE)
    val distance: Double,
    @ColumnInfo(name = ActivityContract.Columns.TIME)
    @Json(name = ActivityContract.Columns.TIME)
    val time: Long,
    @ColumnInfo(name = ActivityContract.Columns.ELEVATION)
    @Json(name = ActivityContract.Columns.ELEVATION)
    val elevation: Long,
    @ColumnInfo(name = ActivityContract.Columns.TYPE)
    @Json(name = ActivityContract.Columns.TYPE)
    val type: String,
    @ColumnInfo(name = ActivityContract.Columns.DATE)
    @Json(name = ActivityContract.Columns.DATE)
    val date: String,
    @ColumnInfo(name = ActivityContract.Columns.DESCRIPTION)
    @Json(name = ActivityContract.Columns.DESCRIPTION)
    val description: String?
)
