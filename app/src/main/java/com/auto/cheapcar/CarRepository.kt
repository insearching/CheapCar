package com.auto.cheapcar

import android.content.SharedPreferences
import com.auto.cheapcar.api.CarsApi
import com.auto.cheapcar.database.AppDatabase
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Date
import com.auto.cheapcar.entity.bo.Type
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CarRepository @Inject constructor(private val prefs: SharedPreferences,
                                        private val carsApi: CarsApi,
                                        private val carsDatabase: AppDatabase) {

    companion object {
        const val MANUFACTURER_PAGE_LOADED = "manufacturer_page_loaded"
        const val MANUFACTURER_PAGE_COUNT = "manufacturer_page_count"
        const val MAIN_TYPE_PAGE_LOADED = "main_type_page_loaded"
        const val MAIN_TYPE_PAGE_COUNT = "main_type_page_count"
    }

    var isLoading = false

    fun getManufacturers(page: Int, pageSize: Int): Flowable<List<Brand>> =
            Flowable.merge(requestManufacturersFromApi(page, pageSize),
                    getBrandsFromDb().toFlowable().filter({ it.isNotEmpty() }))
                    .subscribeOn(Schedulers.io())

    private fun requestManufacturersFromApi(page: Int, pageSize: Int): Flowable<List<Brand>> =
            carsApi.getManufacturers(page, pageSize)
                    .doOnSubscribe({ isLoading = true })
                    .doOnSuccess({ isLoading = false })
                    .doOnSuccess({
                        if (it.titles?.isNotEmpty()!!)
                            prefs.edit().putInt(MANUFACTURER_PAGE_LOADED,
                                    it.page?.plus(1) ?: 0).apply()
                        prefs.edit().putInt(MANUFACTURER_PAGE_COUNT, it.totalPageCount ?: 0).apply()
                    })
                    .map { it.titles?.toList() ?: emptyList() }
                    .flatMapPublisher { Flowable.fromIterable(it) }
                    .map { Brand(it.first, it.second) }
                    .toList()
                    .flatMapCompletable {
                        Completable.fromAction {
                            carsDatabase.brandDao.addBrands(it)
                        }
                    }
                    .andThen(getBrandsFromDb())
                    .toFlowable()
                    .subscribeOn(Schedulers.newThread())

    private fun getBrandsFromDb(): Maybe<List<Brand>> =
            carsDatabase.brandDao.brands
                    .subscribeOn(Schedulers.io())

    fun getMainTypes(manufacturerId: Int, page: Int, pageSize: Int): Flowable<List<Type>> =
            Flowable.merge(requestMainTypesFromApi(manufacturerId, page, pageSize),
                    getMainTypesFromDb(manufacturerId).toFlowable().filter({ it.isNotEmpty() }))
                    .subscribeOn(Schedulers.io())

    private fun requestMainTypesFromApi(manufacturerId: Int, page: Int,
                                        pageSize: Int): Flowable<List<Type>> =
            carsApi.getMainTypes(manufacturerId, page, pageSize)
                    .doOnSubscribe({ isLoading = true })
                    .doOnSuccess({ isLoading = false })
                    .doOnSuccess({
                        if (it.types?.isNotEmpty()!!)
                            prefs.edit().putInt(MAIN_TYPE_PAGE_LOADED,
                                    it.page?.plus(1) ?: 0).apply()
                        prefs.edit().putInt(MAIN_TYPE_PAGE_COUNT, it.totalPageCount ?: 0).apply()
                    })
                    .map { it.types?.toList() ?: emptyList() }
                    .flatMapPublisher { Flowable.fromIterable(it) }
                    .map { Type(it.first, manufacturerId) }
                    .toList()
                    .flatMapCompletable {
                        Completable.fromAction {
                            carsDatabase.mainTypeDao.addTypes(it)
                        }
                    }
                    .andThen(getMainTypesFromDb(manufacturerId))
                    .toFlowable()
                    .subscribeOn(Schedulers.newThread())

    private fun getMainTypesFromDb(manufacturerId: Int): Maybe<List<Type>> =
            carsDatabase.mainTypeDao.getTypesForManufacturer(manufacturerId)
                    .subscribeOn(Schedulers.io())

    fun getBuildDates(manufacturerId: Int, mainType: Type): Flowable<List<Date>> =
            Flowable.merge(requestBuildDates(manufacturerId, mainType),
                    getBuildDatesFromDb(manufacturerId, mainType.id).toFlowable().filter({ it.isNotEmpty() }))
                    .subscribeOn(Schedulers.io())

    private fun requestBuildDates(manufacturerId: Int,
                                  mainType: Type): Flowable<List<Date>> {
        return carsApi.getBuildDates(manufacturerId, mainType.title)
                .doOnSubscribe({ isLoading = true })
                .doOnSuccess({ isLoading = false })
                .map { it.dates.toList() }
                .flatMapPublisher { Flowable.fromIterable(it) }
                .map { Date(it.first, manufacturerId, mainType.id) }
                .toList()
                .flatMapCompletable {
                    Completable.fromAction {
                        carsDatabase.buildDateDao.addBuildDates(it)
                    }
                }
                .andThen(getBuildDatesFromDb(manufacturerId, mainType.id))
                .toFlowable()
                .subscribeOn(Schedulers.newThread())
    }

    private fun getBuildDatesFromDb(manufacturerId: Int, typeId: Int): Maybe<List<Date>> =
            carsDatabase.buildDateDao.getDatesForMainType(manufacturerId, typeId).subscribeOn(Schedulers.io())

    fun getMainTypeIdByTitle(manufacturerId: Int, typeTitle: String): Single<Type> =
            carsDatabase.mainTypeDao.getTypeByTitle(manufacturerId, typeTitle).subscribeOn(Schedulers.io())
}
