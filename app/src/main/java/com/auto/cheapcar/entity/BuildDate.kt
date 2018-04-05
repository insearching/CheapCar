package com.auto.cheapcar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BuildDate (
        @SerializedName("wkda")
        @Expose
        val dates: Map<Long, Long>
)