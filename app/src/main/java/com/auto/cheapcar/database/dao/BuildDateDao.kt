package com.auto.cheapcar.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.auto.cheapcar.entity.bo.Date
import io.reactivex.Maybe

@Dao
interface BuildDateDao {

    @Query("SELECT * FROM `date` WHERE manufacturerId=(:manufacturerId) AND typeId=(:typeId)")
    fun getDatesForMainType(manufacturerId: Int, typeId: Int): Maybe<List<Date>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBuildDates(dates: List<Date>)
}
