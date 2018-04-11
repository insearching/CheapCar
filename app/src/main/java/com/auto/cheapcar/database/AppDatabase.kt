package com.auto.cheapcar.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.auto.cheapcar.database.dao.BrandDao
import com.auto.cheapcar.database.dao.BuildDateDao
import com.auto.cheapcar.database.dao.MainTypeDao
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Date
import com.auto.cheapcar.entity.bo.Type

@Database(entities = arrayOf(Brand::class, Date::class, Type::class),
        version = 1, exportSchema =
false)
abstract class AppDatabase : RoomDatabase() {

    abstract val brandDao: BrandDao

    abstract val mainTypeDao: MainTypeDao

    abstract val buildDateDao: BuildDateDao

}