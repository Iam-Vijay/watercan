package com.watercanedelivery.app.data.cutomer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CustomerListRespDTO(

	@field:SerializedName("customers")
	val customers: ArrayList<CustomersDTO?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)