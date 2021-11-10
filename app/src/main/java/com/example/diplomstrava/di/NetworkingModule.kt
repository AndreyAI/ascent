package com.example.diplomstrava.di

import com.example.diplomstrava.networking.HeaderAuthInterceptor
import com.example.diplomstrava.networking.StravaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    lateinit var accessToken: String

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HeaderAuthInterceptor())
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.strava.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): StravaApi {
        return retrofit.create()
    }
}

/*
@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @AppVersionInterception
    @Provides
    fun providesAppVersionInterceptor(appVersionInterceptor: AppVersionInterceptor): Interceptor {
        return appVersionInterceptor
    }

    @ru.skillbox.dependency_injection.di.HttpLoggingInterceptor
    @Provides
    fun providesHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel((HttpLoggingInterceptor.Level.BODY))
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ru.skillbox.dependency_injection.di.HttpLoggingInterceptor loggingInterceptor: Interceptor,
        @AppVersionInterception appVersionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(appVersionInterceptor)
            .followRedirects(true)
            .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://google.com")
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): Api {
        return retrofit.create()
    }

}
 */