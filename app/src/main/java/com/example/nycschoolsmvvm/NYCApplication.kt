package com.example.nycschoolsmvvm

import android.app.Application
import android.content.Context

class NYCApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        nycApplication = applicationContext
        //init libraries(Timber<logs>, Analytics, DI)
    }

    companion object{
        var nycApplication: Context? = null
    }
}