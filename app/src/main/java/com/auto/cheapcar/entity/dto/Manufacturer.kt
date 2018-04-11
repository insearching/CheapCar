package com.auto.cheapcar.entity.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Manufacturer(
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
    var titles: Map<Int, String>? = null
)