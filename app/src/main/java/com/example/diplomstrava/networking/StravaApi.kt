package com.example.diplomstrava.networking

import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.Person
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StravaApi {

    @GET("athlete/activities")
    fun getActivities(): Call<List<Activity>>

    @GET("athlete")
    fun getPersonData(): Call<Person>

    @POST("activities")
    fun postActivity(@Body activity: Activity): Call<ResponseBody>

    @PUT("athlete")
    fun updatePerson(@Body person: Person): Call<ResponseBody>

    @POST
    fun logout(@Url url: String): Call<ResponseBody>

}