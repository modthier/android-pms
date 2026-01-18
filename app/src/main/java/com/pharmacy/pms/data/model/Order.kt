package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class Order(
    val id: Int,
    val total: Double,
    val discount: Double?,
    @SerializedName("payment_method_id") val paymentMethodId: Int?,
    @SerializedName("shift_id") val shiftId: Int?,
    val items: List<OrderItem>?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class OrderItem(
    val id: Int,
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("stock_id") val stockId: Int,
    val stock: Stock?,
    val quantity: Int,
    val price: Double,
    val discount: Double?
)

data class OrderResponse(
    val data: List<Order>,
    val links: PaginationLinks?,
    val meta: PaginationMeta?
)

data class SingleOrderResponse(
    val data: Order
)

data class CreateOrderRequest(
    val total: Double,
    val discount: Double? = 0.0,
    @SerializedName("payment_method_id") val paymentMethodId: Int?,
    @SerializedName("shift_id") val shiftId: Int?,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    @SerializedName("stock_id") val stockId: Int,
    val quantity: Int,
    val price: Double,
    val discount: Double? = 0.0
)

data class AddOrderItemRequest(
    @SerializedName("stock_id") val stockId: Int,
    val quantity: Int,
    val price: Double,
    val discount: Double? = 0.0
)
