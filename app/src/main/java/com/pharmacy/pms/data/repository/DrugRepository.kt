package com.pharmacy.pms.data.repository

import com.pharmacy.pms.data.api.RetrofitClient
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.model.Drug
import com.pharmacy.pms.data.model.DrugResponse
import com.pharmacy.pms.data.model.SingleDrugResponse

class DrugRepository(
    private val tokenManager: TokenManager
) {
    private fun getApiService(): com.pharmacy.pms.data.api.ApiService {
        val token = tokenManager.getTokenSync()
            ?: throw Exception("Not authenticated")
        return RetrofitClient.createAuthenticatedService(token)
    }
    
    suspend fun getDrugs(perPage: Int? = null, page: Int? = null): Result<DrugResponse> {
        return try {
            val response = getApiService().getDrugs(perPage, page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch drugs"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDrug(id: Int): Result<Drug> {
        return try {
            val response = getApiService().getDrug(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch drug"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchDrugs(query: String, perPage: Int? = null): Result<DrugResponse> {
        return try {
            val response = getApiService().searchDrugs(query, perPage)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Search failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDrugByBarcode(barcode: String): Result<Drug> {
        return try {
            val response = getApiService().getDrugByBarcode(barcode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch drug by barcode"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
