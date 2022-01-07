package com.example.diplomstrava.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(
    tableName = PersonContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersonContract.Columns.ID)
    @Json(name = PersonContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = PersonContract.Columns.LAST_UPDATE)
    @Json(name = PersonContract.Columns.LAST_UPDATE)
    val lastUpdate: String,
    @ColumnInfo(name = PersonContract.Columns.FIRST_NAME)
    @Json(name = PersonContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = PersonContract.Columns.LAST_NAME)
    @Json(name = PersonContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = PersonContract.Columns.COUNTRY)
    @Json(name = PersonContract.Columns.COUNTRY)
    val country: String,
    @ColumnInfo(name = PersonContract.Columns.SEX)
    @Json(name = PersonContract.Columns.SEX)
    val sex: String,
    @ColumnInfo(name = PersonContract.Columns.AVATAR_URL)
    @Json(name = PersonContract.Columns.AVATAR_URL)
    val avatarUrl: String,
    @ColumnInfo(name = PersonContract.Columns.FOLLOWING_COUNT)
    @Json(name = PersonContract.Columns.FOLLOWING_COUNT)
    val followingCount: Long,
    @ColumnInfo(name = PersonContract.Columns.FOLLOWERS_COUNT)
    @Json(name = PersonContract.Columns.FOLLOWERS_COUNT)
    val followersCount: Long,
    @ColumnInfo(name = PersonContract.Columns.WEIGHT)
    @Json(name = PersonContract.Columns.WEIGHT)
    val weight: Double
)