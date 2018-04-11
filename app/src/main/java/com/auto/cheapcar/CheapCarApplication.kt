package com.auto.cheapcar

import android.app.Application
import com.auto.cheapcar.utils.dependencies

class CheapCarApplication : Application() {

    companion object {
        const val WA_KEY = "coding-puzzle-client-449cc9d"
        const val PAGE_SIZE = 15
    }

    override fun onCreate() {
        super.onCreate()

        dependencies().inject(this)
    }
}