package com.auto.cheapcar

import android.app.Application

class CheapCarApplication : Application() {

    companion object {
        const val WA_KEY = "coding-puzzle-client-449cc9d"
        const val PAGE_SIZE = 10
    }

    override fun onCreate() {
        super.onCreate()

    }
}