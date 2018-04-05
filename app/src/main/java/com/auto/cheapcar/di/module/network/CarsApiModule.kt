package com.auto.cheapcar.di.module.network

import com.auto.cheapcar.api.CarsApi
import com.auto.cheapcar.di.scope.FragmentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
open class CarsApiModule {

    @FragmentScope
    @Provides
    fun carsApi(retrofit: Retrofit) = retrofit.create<CarsApi>(CarsApi::class.java) as CarsApi
}