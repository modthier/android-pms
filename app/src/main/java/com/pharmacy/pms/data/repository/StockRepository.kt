package com.pharmacy.pms.data.repository

import com.pharmacy.pms.data.api.RetrofitClient
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.model.Stock
import com.pharmacy.pms.data.model.StockCheckResponse
import com.pharmacy.pms.data.model.StockResponse

class StockRepository(
    private val tokenManager: TokenManager
) {
    private fun getApiService(): com.pharmacy.pms.data.api.ApiService {
        val token = tokenManager.getTokenSync()
            ?: throw Exception("Not authenticated")
        return RetrofitClient.createAuthenticatedService(token)
    }
    
    suspend fun getStock(perPage: Int? = null, page: Int? = null): Result<StockResponse> {
        return try {
            val response = getApiService().getStock(perPage, page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch stock"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getStockItem(id: Int): Result<Stock> {
        return try {
            val response = getApiService().getStockItem(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch stock item"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun checkStock(id: Int, quantity: Int): Result<StockCheckResponse> {
        return try {
            val response = getApiService().checkStock(id, quantity)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to check stock"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getAvailableStock(perPage: Int? = null): Result<StockResponse> {
        return try {
            val response = getApiService().getAvailableStock(perPage)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch available stock"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getExpiredStock(perPage: Int? = null, months: Int? = null): Result<StockResponse> {
        return try {
            val response = getApiService().getExpiredStock(perPage, months)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch expired stock"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
