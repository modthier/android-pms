package com.pharmacy.pms.data.model

import com.google.gson.annotations.SerializedName

data class Drug(
    val id: Int,
    @SerializedName("trade_name") val tradeName: String,
    @SerializedName("generic_name") val genericName: String,
    val barcode: String?,
    @SerializedName("count_per_unit") val countPerUnit: Int,
    val unit: Unit?,
    @SerializedName("item_type") val itemType: ItemType?,
    val stocks: List<Stock>? = null,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class Unit(
    val id: Int,
    val name: String
)

data class ItemType(
    val id: Int,
    val name: String
)

data class DrugResponse(
    val data: List<Drug>,
    val links: PaginationLinks?,
    val meta: PaginationMeta?
)

data class SingleDrugResponse(
    val data: Drug
)
