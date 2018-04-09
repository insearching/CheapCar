package com.auto.cheapcar.entity.bo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "date", foreignKeys = arrayOf (
        ForeignKey(entity = Brand::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("manufacturerId")),
        ForeignKey(entity = Type::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("typeId"))))
data class Date(val date: Int, val manufacturerId: Int, val typeId: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}