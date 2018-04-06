package com.auto.cheapcar.di.component

import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.di.module.DatabaseModule
import com.auto.cheapcar.di.module.network.CarsApiModule
import com.auto.cheapcar.di.module.network.ConnectivityManagerModule
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(ConnectivityManagerModule::class, DatabaseModule::class, CarsApiModule::class))
interface ApplicationComponent {
    fun inject(cheapCarApplication: CheapCarApplication)

    fun repository() : CarRepository
}