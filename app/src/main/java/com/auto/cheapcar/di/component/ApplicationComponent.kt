package com.liftbrands.di.component

import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.di.module.network.ConnectivityManagerModule
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Component
import retrofit2.Retrofit

@ApplicationScope
@Component(modules = [(ConnectivityManagerModule::class)])
interface ApplicationComponent {
    fun retrofit(): Retrofit

    fun inject(cheapCarApplication: CheapCarApplication)
}