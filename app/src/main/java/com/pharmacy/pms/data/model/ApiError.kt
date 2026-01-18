package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    val message: String,
    val errors: Map<String, List<String>>? = null
)
