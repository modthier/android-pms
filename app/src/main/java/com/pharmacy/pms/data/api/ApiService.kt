package com.pharmacy.pms.data.api

import com.google.gson.annotations.SerializedName
import com.pharmacy.pms.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>
    
    @GET("auth/user")
    suspend fun getCurrentUser(): Response<ApiResponse<User>>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
    
    // Drugs
    @GET("drugs")
    suspend fun getDrugs(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Response<DrugResponse>
    
    @GET("drugs/{id}")
    suspend fun getDrug(@Path("id") id: Int): Response<SingleDrugResponse>
    
    @GET("drugs/search/{query}")
    suspend fun searchDrugs(
        @Path("query") query: String,
        @Query("per_page") perPage: Int? = null
    ): Response<DrugResponse>
    
    @GET("drugs/barcode/{barcode}")
    suspend fun getDrugByBarcode(@Path("barcode") barcode: String): Response<SingleDrugResponse>
    
    @POST("drugs")
    suspend fun createDrug(@Body drug: CreateDrugRequest): Response<SingleDrugResponse>
    
    @PUT("drugs/{id}")
    suspend fun updateDrug(@Path("id") id: Int, @Body drug: UpdateDrugRequest): Response<SingleDrugResponse>
    
    @DELETE("drugs/{id}")
    suspend fun deleteDrug(@Path("id") id: Int): Response<ApiResponse<Unit>>
    
    // Stock
    @GET("stock")
    suspend fun getStock(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Response<StockResponse>
    
    @GET("stock/{id}")
    suspend fun getStockItem(@Path("id") id: Int): Response<SingleStockResponse>
    
    @GET("stock/check/{id}/{quantity}")
    suspend fun checkStock(
        @Path("id") id: Int,
        @Path("quantity") quantity: Int
    ): Response<StockCheckResponse>
    
    @GET("stock/available")
    suspend fun getAvailableStock(
        @Query("per_page") perPage: Int? = null
    ): Response<StockResponse>
    
    @GET("stock/expired")
    suspend fun getExpiredStock(
        @Query("per_page") perPage: Int? = null,
        @Query("months") months: Int? = null
    ): Response<StockResponse>
    
    // Orders
    @GET("orders")
    suspend fun getOrders(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Response<OrderResponse>
    
    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id: Int): Response<SingleOrderResponse>
    
    @POST("orders")
    suspend fun createOrder(@Body request: CreateOrderRequest): Response<SingleOrderResponse>
    
    @POST("orders/{id}/items")
    suspend fun addOrderItem(
        @Path("id") orderId: Int,
        @Body request: AddOrderItemRequest
    ): Response<SingleOrderResponse>
    
    @DELETE("orders/{id}/items/{itemId}")
    suspend fun removeOrderItem(
        @Path("id") orderId: Int,
        @Path("itemId") itemId: Int
    ): Response<SingleOrderResponse>
    
    @POST("orders/{id}/complete")
    suspend fun completeOrder(@Path("id") id: Int): Response<SingleOrderResponse>
    
    // User Profile
    @GET("user/profile")
    suspend fun getProfile(): Response<ApiResponse<User>>
    
    @PUT("user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<ApiResponse<User>>
}

// Helper data classes
data class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
)

data class CreateDrugRequest(
    @SerializedName("trade_name") val tradeName: String,
    @SerializedName("generic_name") val genericName: String,
    @SerializedName("item_type_id") val itemTypeId: Int,
    @SerializedName("unit_id") val unitId: Int,
    @SerializedName("count_per_unit") val countPerUnit: Int,
    val barcode: String? = null
)

data class UpdateDrugRequest(
    @SerializedName("trade_name") val tradeName: String? = null,
    @SerializedName("generic_name") val genericName: String? = null,
    @SerializedName("item_type_id") val itemTypeId: Int? = null,
    @SerializedName("unit_id") val unitId: Int? = null,
    @SerializedName("count_per_unit") val countPerUnit: Int? = null,
    val barcode: String? = null
)

data class UpdateProfileRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    @SerializedName("password_confirmation") val passwordConfirmation: String? = null,
    @SerializedName("current_password") val currentPassword: String? = null
)
