package com.auto.cheapcar.database.dao

import android.arch.persistence.room.*
import com.auto.cheapcar.entity.bo.Brand
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface BrandDao {

    @get:Query("SELECT * FROM `brand`")
    val brands: Maybe<List<Brand>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBrands(brands: List<Brand>)

    @Transaction
    @Query("SELECT * FROM `brand` WHERE id=(:brandId)")
    fun getBrand(brandId: Long): Single<Brand>
}
