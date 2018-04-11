package com.auto.cheapcar.entity.bo

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "type", foreignKeys = arrayOf(
        ForeignKey(entity = Brand::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("manufacturerId"))))
data class Type(val title: String, val manufacturerId: Int) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}