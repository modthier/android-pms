package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class PaginationLinks(
    val first: String?,
    val last: String?,
    val prev: String?,
    val next: String?
)

data class PaginationMeta(
    @SerializedName("current_page") val currentPage: Int,
    val from: Int?,
    @SerializedName("last_page") val lastPage: Int,
    val path: String,
    @SerializedName("per_page") val perPage: Int,
    val to: Int?,
    val total: Int
)
