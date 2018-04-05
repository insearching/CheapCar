package com.auto.cheapcar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Manufacturer (
    @SerializedName("page")
    @Expose
    var page: Int? = null,
    @SerializedName("pageSize")
    @Expose
    var pageSize: Int? = null,
    @SerializedName("totalPageCount")
    @Expose
    var totalPageCount: Int? = null,
    @SerializedName("wkda")
    @Expose
    var names: Map<Int, String>? = null
)