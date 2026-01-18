package com.pharmacy.pms.data.repository

import com.pharmacy.pms.data.api.ApiService
import com.pharmacy.pms.data.api.RetrofitClient
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.model.AuthResponse
import com.pharmacy.pms.data.model.LoginRequest
import com.pharmacy.pms.data.model.RefreshTokenRequest
import com.pharmacy.pms.data.model.RefreshTokenResponse

class AuthRepository(
    private val tokenManager: TokenManager
) {
    private val apiService: ApiService = RetrofitClient.apiService
    
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveUserEmail(authResponse.user.email)
                Result.success(authResponse)
            } else {
                Result.failure(Exception(response.message() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Unit> {
        return try {
            val token = tokenManager.getTokenSync()
            if (token != null) {
                val authenticatedService = RetrofitClient.createAuthenticatedService(token)
                authenticatedService.logout()
            }
            tokenManager.clearToken()
            Result.success(Unit)
        } catch (e: Exception) {
            tokenManager.clearToken()
            Result.failure(e)
        }
    }
    
    suspend fun refreshToken(): Result<RefreshTokenResponse> {
        return try {
            val token = tokenManager.getTokenSync()
            if (token == null) {
                return Result.failure(Exception("No token available"))
            }
            val authenticatedService = RetrofitClient.createAuthenticatedService(token)
            val request = RefreshTokenRequest()
            val response = authenticatedService.refreshToken(request)
            if (response.isSuccessful && response.body() != null) {
                val refreshResponse = response.body()!!
                tokenManager.saveToken(refreshResponse.token)
                Result.success(refreshResponse)
            } else {
                Result.failure(Exception(response.message() ?: "Token refresh failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getToken(): String? {
        return tokenManager.getTokenSync()
    }
}
