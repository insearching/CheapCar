package com.auto.cheapcar.api

import com.auto.cheapcar.entity.dto.BuildDate
import com.auto.cheapcar.entity.dto.MainType
import com.auto.cheapcar.entity.dto.Manufacturer
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class CarsApiMock : CarsApi {

    override fun getManufacturers(page: Int, pageSize: Int, waKey: String): Single<Manufacturer> {
//        val titles = emptyMap<Int, String>()
        val titles = mapOf(Pair(0, "Alpha"), Pair(1, "Romo"), Pair(3, "Bravo"), Pair(4, "Charley"))
        return Single.just(Manufacturer(0, 15, 4, titles)).delay(3, TimeUnit.SECONDS)
    }

    override fun getMainTypes(manufacturer: Int, page: Int, pageSize: Int, waKey: String):
            Single<MainType> {
//        val types = emptyMap<String, String>()
        val types = mapOf(Pair("Uno", "Uno"), Pair("Dos", "Dos"), Pair("Tres", "Tres"))
        return Single.just(MainType(0, 15, 2, types)).delay(2, TimeUnit.SECONDS)
    }

    override fun getBuildDates(manufacturer: Int, mainType: String, waKey: String): Single<BuildDate> {
        val dates = mapOf(Pair(2000, 2000), Pair(2001, 2001), Pair(2002, 2002))
        return Single.just(BuildDate(dates))
    }
}