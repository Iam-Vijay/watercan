package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CustomerResponseDTO(
	@SerializedName("order_detail")
	val customers: ArrayList<CustomersDTO?>? = null,
	val status: Boolean? = null
)