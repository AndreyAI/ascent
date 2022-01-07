package com.example.diplomstrava

import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.ActivityContract
import com.example.diplomstrava.data.Athlete
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class ActivityCustomAdapter {

    @FromJson
    fun fromJson(customActivity: CustomActivity): Activity {
        return Activity(
            id = customActivity.id,
            name = customActivity.name,
            distance = customActivity.distance,
            time = customActivity.time,
            elevation = customActivity.elevation,
            type = customActivity.type,
            date = customActivity.date,
            description = customActivity.description
        )
    }

    @JsonClass(generateAdapter = true)
    data class CustomActivity(
        @Json(name = ActivityContract.Columns.ID)
        val id: Long,
        @Json(name = ActivityContract.Columns.PERSON_ID)
        val personId: Athlete,
        @Json(name = ActivityContract.Columns.NAME)
        val name: String,
        @Json(name = ActivityContract.Columns.DISTANCE)
        val distance: Double,
        @Json(name = ActivityContract.Columns.TIME)
        val time: Long,
        @Json(name = ActivityContract.Columns.ELEVATION)
        val elevation: Long,
        @Json(name = ActivityContract.Columns.TYPE)
        val type: String,
        @Json(name = ActivityContract.Columns.DATE)
        val date: String,
        @Json(name = ActivityContract.Columns.DESCRIPTION)
        val description: String?
    )



}
