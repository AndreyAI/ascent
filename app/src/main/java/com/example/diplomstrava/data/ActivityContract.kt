package com.example.diplomstrava.data

object ActivityContract {

    const val TABLE_NAME = "activities"

    object Columns {
        const val ID = "id"
        const val PERSON_ID = "athlete"
        const val NAME = "name"
        const val DISTANCE = "distance"
        const val TIME = "elapsed_time"
        const val ELEVATION = "total_elevation_gain"
        const val TYPE = "type"
        const val DATE = "start_date_local"
        const val DESCRIPTION = "description"
    }
}