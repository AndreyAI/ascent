package com.example.diplomstrava.data.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://www.strava.com/oauth/mobile/authorize"
    const val TOKEN_URI = "https://www.strava.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "activity:write,activity:read_all,profile:read_all,profile:write"
    const val APPROVAL_PROMPT = "auto"

    const val CLIENT_ID = "73381"
    const val CLIENT_SECRET = "e1472e76e5d66e5a29d55255bc59f66fcdf45576"
    const val CALLBACK_URL = "diplomstrava://developers.strava.com"
}