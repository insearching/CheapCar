package com.auto.cheapcar.di.component

import android.content.SharedPreferences
import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.di.module.DatabaseModule
import com.auto.cheapcar.di.module.android.SharedPreferenceModule
import com.auto.cheapcar.di.module.manager.ConnectivityManagerModule
import com.auto.cheapcar.di.module.network.CarsApiModule
import com.auto.cheapcar.di.scope.ApplicationScope
import com.auto.cheapcar.utils.manager.InternetManager
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(ConnectivityManagerModule::class, DatabaseModule::class,
        CarsApiModule::class, SharedPreferenceModule::class))
interface ApplicationComponent {
    fun inject(cheapCarApplication: CheapCarApplication)

    fun repository(): CarRepository

    fun sharedPreferences(): SharedPreferences

    fun internetManager(): InternetManager
}