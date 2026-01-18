package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val roles: List<Role>? = null,
    val permissions: List<Permission>? = null
)

data class Role(
    val id: Int,
    val name: String
)

data class Permission(
    val id: Int,
    val name: String
)
