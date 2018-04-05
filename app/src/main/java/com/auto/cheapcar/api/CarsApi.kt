package com.auto.cheapcar.api

import com.auto.cheapcar.entity.BuildDate
import com.auto.cheapcar.entity.MainType
import com.auto.cheapcar.entity.Manufacturer
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CarsApi {

    @GET("manufacturer")
    fun getManufacturers(@Query("page") page: Int,
                         @Query("pageSize") pageSize: Int,
                         @Query("wa_key") waKey: String): Single<Manufacturer>

    @GET("main-types")
    fun getMainTypes(
            @Query("manufacturer") manufacturer: Int,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("wa_key") waKey: String): Single<MainType>

    @GET("built-dates")
    fun getBuildDates(@Query("manufacturer") manufacturer: Int,
                      @Query("main-type") mainType: String,
                      @Query("wa_key") waKey: String): Single<BuildDate>

    companion object {
        fun create(): CarsApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api-aws-eu-qa-1.auto1-test.com/v1/car-types/")
                    .client(client)
                    .build()

            return retrofit.create(CarsApi::class.java)
        }
    }
}