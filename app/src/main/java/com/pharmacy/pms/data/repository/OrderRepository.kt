package com.pharmacy.pms.data.repository

import com.pharmacy.pms.data.api.RetrofitClient
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.model.AddOrderItemRequest
import com.pharmacy.pms.data.model.CreateOrderRequest
import com.pharmacy.pms.data.model.Order
import com.pharmacy.pms.data.model.OrderResponse
import com.pharmacy.pms.data.model.SingleOrderResponse

class OrderRepository(
    private val tokenManager: TokenManager
) {
    private fun getApiService(): com.pharmacy.pms.data.api.ApiService {
        val token = tokenManager.getTokenSync()
            ?: throw Exception("Not authenticated")
        return RetrofitClient.createAuthenticatedService(token)
    }
    
    suspend fun getOrders(perPage: Int? = null, page: Int? = null): Result<OrderResponse> {
        return try {
            val response = getApiService().getOrders(perPage, page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch orders"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getOrder(id: Int): Result<Order> {
        return try {
            val response = getApiService().getOrder(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to fetch order"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createOrder(request: CreateOrderRequest): Result<Order> {
        return try {
            val response = getApiService().createOrder(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to create order"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addOrderItem(orderId: Int, request: AddOrderItemRequest): Result<Order> {
        return try {
            val response = getApiService().addOrderItem(orderId, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to add item"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeOrderItem(orderId: Int, itemId: Int): Result<Order> {
        return try {
            val response = getApiService().removeOrderItem(orderId, itemId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to remove item"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun completeOrder(id: Int): Result<Order> {
        return try {
            val response = getApiService().completeOrder(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to complete order"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
