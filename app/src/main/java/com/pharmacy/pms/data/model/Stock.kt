package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class Stock(
    val id: Int,
    @SerializedName("drug_id") val drugId: Int,
    val drug: Drug?,
    val quantity: Int,
    @SerializedName("expiry_date") val expiryDate: String?,
    @SerializedName("purchase_price") val purchasePrice: Double?,
    @SerializedName("selling_price") val sellingPrice: Double?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class StockResponse(
    val data: List<Stock>,
    val links: PaginationLinks?,
    val meta: PaginationMeta?
)

data class SingleStockResponse(
    val data: Stock
)

data class StockCheckResponse(
    val available: Boolean,
    val message: String,
    @SerializedName("stock_quantity") val stockQuantity: Int,
    @SerializedName("requested_quantity") val requestedQuantity: Int
)
