package com.auto.cheapcar.api

import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.entity.dto.BuildDate
import com.auto.cheapcar.entity.dto.MainType
import com.auto.cheapcar.entity.dto.Manufacturer
import io.reactivex.Single
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class CarsApiMock {

    fun getManufacturers(page: Int, pageSize: Int, waKey: String = CheapCarApplication.WA_KEY): Single<Manufacturer> {
        val titles = mapOf(Pair(0, "Alpha"), Pair(1, "Romo"), Pair(3, "Bravo"), Pair(4, "Charley"))
        return Single.just(Manufacturer(0 , 15, 4, titles)).delay(3, TimeUnit.SECONDS)
    }

    fun getMainTypes(
            @Query("manufacturer") manufacturer: Int,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("wa_key") waKey: String = CheapCarApplication.WA_KEY): Single<MainType> {
        val types = mapOf(Pair("Uno", "Uno"), Pair("Dos", "Dos"), Pair("Tres", "Tres"))
        return Single.just(MainType(0, 15, 2, types))
    }

    fun getBuildDates(manufacturer: Int, mainType: String, waKey: String = CheapCarApplication.WA_KEY): Single<BuildDate> {
        val dates = mapOf(Pair(2000, 2000), Pair(2001, 2001), Pair(2002, 2002))
        return Single.just(BuildDate(dates))
    }
}