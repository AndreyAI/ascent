package com.example.diplomstrava.networking

import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.Person
import retrofit2.Call
import retrofit2.http.GET

interface StravaApi {

    @GET("athlete/activities")
    fun getActivities(): Call<List<Activity>>

    @GET("athlete")
    fun getPersonData(): Call<Person>

}

/*
interface GithubApi {
    @GET("user")
    fun getUserInfo(): Call<CurrentUserData>

    @PATCH("user")
    fun updateUserInfo(
        @Body name: Map<String, String>
    ): Call<CurrentUserData>

    @GET("repositories")
    fun getPublicRepositories(): Call<List<PublicRepositories>>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): Call<Boolean>

    @PUT("user/starred/{owner}/{repo}")
    fun starRepository(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): Call<Boolean>

    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepository(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): Call<Boolean>
}
 */