package com.example.diplomstrava.data

object PersonContract {

    const val TABLE_NAME = "person"

    object Columns {
        const val ID = "id"
        const val LAST_UPDATE = "updated_at"
        const val FIRST_NAME = "firstname"
        const val LAST_NAME = "lastname"
        const val COUNTRY = "country"
        const val SEX = "sex"
        const val AVATAR_URL = "profile"
        const val FOLLOWING_COUNT = "friend_count"
        const val FOLLOWERS_COUNT = "follower_count"
        const val WEIGHT = "weight"

    }
}
/*
data class Person(
    @Json(name = ")
    val lastUpdate: String,
    @Json(name = "updated_at")
    val lastUpdate: String,
    @Json(name = "firstname")
    val firstName: String,
    @Json(name = "lastname")
    val lastName: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "sex")
    val sex: String,
    @Json(name = "profile")
    val avatarUrl: String,
    @Json(name = "friend_count")
    val followingCount: Long,
    @Json(name = "follower_count")
    val followersCount: Long,
    @Json(name = "weight")
    val weight: Double
)*/