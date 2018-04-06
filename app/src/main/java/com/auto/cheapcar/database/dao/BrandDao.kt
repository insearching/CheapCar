package com.auto.cheapcar.database.dao

import android.arch.persistence.room.*
import com.auto.cheapcar.entity.bo.Brand
import io.reactivex.Single

@Dao
interface BrandDao {

    @get:Query("SELECT * FROM `brand`")
    val brands: Single<List<Brand>>

    @get:Query("SELECT COUNT(*) FROM `brand`")
    val brandCount: Single<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBrands(brands: List<Brand>)

    @Transaction
    @Query("SELECT * FROM `brand` WHERE id=(:brandId)")
    fun getBrands(brandId: Long): Single<Brand>
}
