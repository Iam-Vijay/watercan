package com.watercanedelivery.app.data.cutomer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CustomersDTO(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mobile")
	val mobileNo: String? = null,

	@field:SerializedName("deposit_amount")
	val depositAmount: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("in_cane1")
	val inCane1: String? = null,

	@field:SerializedName("in_cane2")
	val inCane2: String? = null,

	@field:SerializedName("in_cane3")
	val inCane3: String? = null,

	@field:SerializedName("in_cane4")
	val inCane4: String? = null,

	@field:SerializedName("in_cane5")
	val inCane5: String? = null,

	@field:SerializedName("out_cane1")
	val outCane1: String? = null,

	@field:SerializedName("out_cane2")
	val outCane2: String? = null,

	@field:SerializedName("out_cane3")
	val outCane3: String? = null,

	@field:SerializedName("out_cane4")
	val outCane4: String? = null,

	@field:SerializedName("out_cane5")
	val outCane5: String? = null,

	@field:SerializedName("is_status")
	val isStatus: String? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("created_by")
	val createdBy: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: String? = null
)