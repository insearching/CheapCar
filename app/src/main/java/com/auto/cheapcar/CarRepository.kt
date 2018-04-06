package com.auto.cheapcar

import com.auto.cheapcar.api.CarsApi
import com.auto.cheapcar.database.AppDatabase
import com.auto.cheapcar.entity.bo.Brand
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CarRepository @Inject constructor(private val carsApi: CarsApi, private val carsDatabase: AppDatabase) {

    fun getManufacturers(page: Int, pageSize: Int): Flowable<List<Brand>> =
            Flowable.concat(getManufacturersFromDb().toFlowable(), getManufacturersWithUpdates(page, pageSize))
                    .flatMap { Flowable.fromIterable(it) }
                    .distinct()
                    .toList()
                    .toFlowable()
                    .subscribeOn(Schedulers.io())

    private fun getManufacturersWithUpdates(page: Int, pageSize: Int) : Flowable<List<Brand>>{
        return requestManufacturersFromApi(page, pageSize).doOnSuccess { saveManufacturersToDb(it) }
                .flatMapPublisher {getManufacturersFromDb().toFlowable() }
                .subscribeOn(Schedulers.newThread())
    }

    private fun requestManufacturersFromApi(page: Int, pageSize: Int): Single<List<Brand>> =
            carsApi.getManufacturers(page, pageSize)
                    .map { it.titles?.toList() ?: emptyList() }
                    .flatMapPublisher { Flowable.fromIterable(it) }
                    .map { Brand(it.first, it.second) }
                    .toList()
                    .subscribeOn(Schedulers.newThread())

    private fun saveManufacturersToDb(brands: List<Brand>) = carsDatabase.brandDao.addBrands(brands)

    private fun getManufacturersFromDb(): Single<List<Brand>> = carsDatabase.brandDao.brands.subscribeOn(Schedulers.io())


//    fun getMainTypes(): Single<List<String>> {
//
//    }
//
//    fun getBuildDates(): Single<List<String>> {
//
//    }
}