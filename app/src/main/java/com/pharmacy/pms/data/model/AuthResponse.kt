package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val message: String,
    val user: User,
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String,
    @SerializedName("device_name") val deviceName: String = "Android App"
)

data class RefreshTokenRequest(
    @SerializedName("device_name") val deviceName: String = "Android App"
)

data class RefreshTokenResponse(
    val message: String,
    val token: String
)
