package com.auto.cheapcar.entity.bo

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "brand")
data class Brand(@PrimaryKey(autoGenerate = false) val id: Int, val title: String) : Parcelable