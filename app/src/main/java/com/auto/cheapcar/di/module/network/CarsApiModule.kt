package com.auto.cheapcar.di.module.network

import com.auto.cheapcar.api.CarsApi
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module (includes = arrayOf(RetrofitModule::class))
class CarsApiModule {

    @Provides
    @ApplicationScope
    fun carsApi(retrofit: Retrofit) = retrofit.create<CarsApi>(CarsApi::class.java) as CarsApi
}