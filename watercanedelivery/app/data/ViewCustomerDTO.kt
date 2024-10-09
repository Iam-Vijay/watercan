package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewCustomerDTO(
	@SerializedName("order_detail")
	val orderDetail: OrderDetailDTO? = null,
	val status: Boolean? = null,
	@SerializedName("in_cane")
val inCane:IncaneDetailDTO?=null
)