package com.auto.cheapcar.di.module.network

import com.auto.cheapcar.api.CarsApiMock
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = arrayOf(RetrofitModule::class))
class CarsApiModule {

    @Provides
    @ApplicationScope
    fun carsApi(retrofit: Retrofit) =
//            retrofit.create<CarsApiMock>(CarsApiMock::class.java) as CarsApiMock
    CarsApiMock()
}