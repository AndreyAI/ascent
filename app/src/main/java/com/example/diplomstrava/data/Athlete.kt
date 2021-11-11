package com.example.diplomstrava.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Athlete(
    @Json(name = "id")
    val id: Long,
    @Json(name = "resource_state")
    val state: Long
)
