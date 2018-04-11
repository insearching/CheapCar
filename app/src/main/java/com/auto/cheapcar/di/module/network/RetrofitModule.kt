package com.auto.cheapcar.di.module.network

import android.content.Context
import com.auto.cheapcar.di.module.android.ContextModule
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = arrayOf(ContextModule::class))
class RetrofitModule {

    @ApplicationScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl("http://api-aws-eu-qa-1.auto1-test.com/v1/car-types/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()!!

    @ApplicationScope
    @Provides
    fun cacheFile(context: Context) = File(context.cacheDir, "okhttp_cache")

    @ApplicationScope
    @Provides
    fun cache(cacheFile: File): Cache = Cache(cacheFile, 64 * 1024 * 1024)

    @ApplicationScope
    @Provides
    fun okHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor) =
            OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .cache(cache)
                    .build()!!


    @ApplicationScope
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}