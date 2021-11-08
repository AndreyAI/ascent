package com.example.diplomstrava.data.auth

import net.openid.appauth.ResponseTypeValues

object  AuthConfig {

//    const val AUTH_URI = "https://github.com/login/oauth/authorize"
//    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
//    const val RESPONSE_TYPE = ResponseTypeValues.CODE
//    const val SCOPE = "user,repo"
//
//    const val CLIENT_ID = "d32125930c9cf4b07ccc"
//    const val CLIENT_SECRET = "c3ed32408d9951c2b38808798079d18b0d95d52e"
//    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"

    const val AUTH_URI = "https://www.strava.com/oauth/mobile/authorize"
    const val TOKEN_URI = "https://www.strava.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "activity:write,activity:read"
    const val APPROVAL_PROMPT= "auto"

    const val CLIENT_ID = "73381"
    const val CLIENT_SECRET = "e1472e76e5d66e5a29d55255bc59f66fcdf45576"
    const val CALLBACK_URL = "diplomstrava://developers.strava.com"
}