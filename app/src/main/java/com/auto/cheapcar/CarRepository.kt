package com.auto.cheapcar

import android.content.SharedPreferences
import com.auto.cheapcar.api.CarsApiMock
import com.auto.cheapcar.database.AppDatabase
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Date
import com.auto.cheapcar.entity.bo.Type
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CarRepository @Inject constructor(private val prefs: SharedPreferences,
                                        private val carsApi: CarsApiMock,
                                        private val carsDatabase: AppDatabase) {

    companion object {
        const val MANUFACTURER_PAGE_LOADED = "manufacturer_page_loaded"
        const val MANUFACTURER_PAGE_COUNT = "manufacturer_page_count"
        const val MAIN_TYPE_PAGE_LOADED = "main_type_page_loaded"
        const val MAIN_TYPE_PAGE_COUNT = "main_type_page_count"
    }

    internal var isLoading = false

    internal fun getManufacturers(page: Int, pageSize: Int): Flowable<List<Brand>> =
            Flowable.concat(getBrandsFromDb().toFlowable(),
                    requestManufacturersFromApi(page, pageSize))
                    .flatMap { Flowable.fromIterable(it) }
                    .distinct()
                    .toList()
                    .toFlowable()
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
                    .toFlowable()
                    .doOnNext({
                        saveBrandsToDb(it)
                    })
                    .subscribeOn(Schedulers.newThread())

    private fun saveBrandsToDb(brands: List<Brand>) = carsDatabase.brandDao.addBrands(brands)

    private fun getBrandsFromDb(): Single<List<Brand>> = carsDatabase.brandDao.brands.subscribeOn(Schedulers.io())

    internal fun getMainTypes(manufacturerId: Int, page: Int, pageSize: Int): Flowable<List<Type>> =
            Flowable.concat(getMainTypesFromDb(manufacturerId).toFlowable(),
                    requestMainTypesFromApi(manufacturerId, page, pageSize))
                    .flatMap { Flowable.fromIterable(it) }
                    .distinct()
                    .toList()
                    .toFlowable()
                    .subscribeOn(Schedulers.io())

    private fun requestMainTypesFromApi(manufacturerId: Int,
                                        page: Int,
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
                    .toFlowable()
                    .doOnNext({
                        saveMainTypesToDb(it)
                    })
                    .subscribeOn(Schedulers.newThread())

    private fun saveMainTypesToDb(types: List<Type>) = carsDatabase.mainTypeDao.addTypes(types)

    private fun getMainTypesFromDb(manufacturerId: Int): Single<List<Type>> = carsDatabase
            .mainTypeDao.getTypesForManufacturer(manufacturerId)
            .subscribeOn(Schedulers.io())

    internal fun getBuildDates(manufacturerId: Int,
                               mainType: Type): Flowable<List<Date>> =
            Flowable.concat(getBuildDatesFromDb(manufacturerId, mainType.id).toFlowable(),
                    requestBuildDates(manufacturerId, mainType).toFlowable())
                    .flatMap { Flowable.fromIterable(it) }
                    .distinct()
                    .toList()
                    .toFlowable()
                    .subscribeOn(Schedulers.io())

    private fun requestBuildDates(manufacturerId: Int,
                                  mainType: Type): Single<List<Date>> {
        return carsApi.getBuildDates(manufacturerId, mainType.title)
                .doOnSubscribe({ isLoading = true })
                .doOnSuccess({ isLoading = false })
                .map { it.dates.toList() }
                .flatMapPublisher { Flowable.fromIterable(it) }
                .map { Date(it.first, manufacturerId, mainType.id) }
                .toList()
                .doOnSuccess({
                    saveBuildDatesToDb(it)
                })
                .subscribeOn(Schedulers.newThread())
    }

    private fun saveBuildDatesToDb(dates: List<Date>) = carsDatabase.buildDateDao.addBuildDates(dates)

    private fun getBuildDatesFromDb(manufacturerId: Int, typeId: Int): Single<List<Date>> =
            carsDatabase.buildDateDao.getDatesForMainType(manufacturerId, typeId).subscribeOn(Schedulers.io())

    internal fun getMainTypeIdByTitle(manufacturerId: Int, typeTitle: String) : Single<Type> =
            carsDatabase.mainTypeDao.getTypeByTitle(manufacturerId, typeTitle).subscribeOn(Schedulers.io())
}