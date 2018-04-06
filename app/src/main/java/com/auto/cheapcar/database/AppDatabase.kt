package com.auto.cheapcar.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.auto.cheapcar.database.dao.BrandDao
import com.auto.cheapcar.entity.bo.Brand

@Database(entities = arrayOf(Brand::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val brandDao: BrandDao

}