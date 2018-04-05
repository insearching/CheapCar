package com.auto.cheapcar.di.module.network

import android.content.Context
import com.auto.cheapcar.di.scope.ApplicationScope
import com.liftbrands.di.module.android.SharedPreferenceModule
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Module
(includes = [(SharedPreferenceModule::class)])
class NetworkModule {

    @ApplicationScope
    @Provides
    fun cacheFile(context: Context) = File(context.cacheDir, "okhttp_cache")

    @ApplicationScope
    @Provides
    fun cache(cacheFile: File): Cache = Cache(cacheFile, 8 * 1024 * 1024)

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
