package com.auto.cheapcar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MainType (
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
    var types: Map<String, String>? = null
)