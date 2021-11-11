package com.example.diplomstrava.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.Json


data class PersonWithActivity(
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val activity: Activity
)