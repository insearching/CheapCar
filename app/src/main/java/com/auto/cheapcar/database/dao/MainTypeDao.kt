package com.auto.cheapcar.database.dao

import android.arch.persistence.room.*
import com.auto.cheapcar.entity.bo.Type
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface MainTypeDao {

    @Query("SELECT * FROM `type` WHERE manufacturerId=(:manufacturerId)")
    fun getTypesForManufacturer(manufacturerId: Int): Maybe<List<Type>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTypes(types: List<Type>)

    @Transaction
    @Query("SELECT * FROM `type` WHERE manufacturerId=(:manufacturerId) AND title=(:title)")
    fun getTypeByTitle(manufacturerId: Int, title: String): Single<Type>
}
