package com.example.nycschoolsmvvm.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import kotlin.math.log

interface NYCApi {
    //https://data.cityofnewyork.us/resource/f9bf-2cp4.json
    //https://data.cityofnewyork.us/resource/s3k6-pzi2.json
    @GET("resource/s3k6-pzi2.json")
    fun getListOfSchools(): Call<List<NYCResponse>>

    @GET("resource/f9bf-2cp4.json")
    fun getListOfSATScore(): Call<List<NYCSATResponse>>
}
    object NYCRetrofit{
        var INSTANCE: NYCApi? = null
        fun initRetrofit(): NYCApi{
            var tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            tempInstance =Retrofit.Builder()
                .client(createLoggerClient())
                .baseUrl("https://data.cityofnewyork.us/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NYCApi::class.java)
            return tempInstance
        }

        fun createLoggerClient(): OkHttpClient{
            val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
            logger.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return okHttpClient
        }
    }
