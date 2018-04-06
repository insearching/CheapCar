package com.auto.cheapcar.api

import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.entity.dto.BuildDate
import com.auto.cheapcar.entity.dto.MainType
import com.auto.cheapcar.entity.dto.Manufacturer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsApi {

    @GET("manufacturer")
    fun getManufacturers(@Query("page") page: Int,
                         @Query("pageSize") pageSize: Int,
                         @Query("wa_key") waKey: String = CheapCarApplication.WA_KEY): Single<Manufacturer>

    @GET("main-types")
    fun getMainTypes(
            @Query("manufacturer") manufacturer: Int,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("wa_key") waKey: String = CheapCarApplication.WA_KEY): Single<MainType>

    @GET("built-dates")
    fun getBuildDates(@Query("manufacturer") manufacturer: Int,
                      @Query("main-type") mainType: String,
                      @Query("wa_key") waKey: String = CheapCarApplication.WA_KEY): Single<BuildDate>
}