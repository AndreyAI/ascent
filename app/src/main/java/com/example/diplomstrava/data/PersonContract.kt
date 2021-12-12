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