package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDetailDTO(
    val id: String? = null,
    @SerializedName("customer_id")
    val customerId: String? = null,
    val name: String? = null,
    val mobile: String? = null,
    val address: String? = null,
    val deliveryDate: String? = null,
    val notes: String? = null,

    val brand: String? = null,
    val quantity: String? = null,

    @SerializedName("paid_amount")
    val paidAmount: String? = null,

    @SerializedName("order_status")
    val orderStatus: String? = null,
    @SerializedName("in_cane1")
    val inCane1: String? = null, @SerializedName("in_cane2")

    val inCane2: String? = null, @SerializedName("in_cane3")

    val inCane3: String? = null, @SerializedName("in_cane4")

    val inCane4: String? = null, @SerializedName("in_cane5")

    val inCane5: String? = null,
	@SerializedName("out_cane1")

	val outCane1: String? = null,	@SerializedName("out_cane2")

	val outCane2: String? = null,	@SerializedName("out_cane3")

	val outCane3: String? = null,	@SerializedName("out_cane4")

	val outCane4: String? = null,	@SerializedName("out_cane5")

	val outCane5: String? = null,
    val isDeleted: String? = null,
    val createdAt: String? = null,
    val createdBy: String? = null,
    val updatedAt: String? = null,
    val updatedBy: String? = null,
    val cane_amount: String? = null,
    val pending_amount: String? = null,
    val payable_amount: String? = null
)