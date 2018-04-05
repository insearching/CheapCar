package com.auto.cheapcar.di.module.network

import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Ivan Lavryshyn
 */

@Module(includes = [(NetworkModule::class)])
open class RetrofitModule {

    @ApplicationScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl("http://api-aws-eu-qa-1.auto1-test.com/v1/car-types/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()!!
}