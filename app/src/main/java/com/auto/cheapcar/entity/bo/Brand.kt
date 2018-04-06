package com.auto.cheapcar.entity.bo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "brand")
data class Brand(@PrimaryKey(autoGenerate = false) val id: Int, val title: String)