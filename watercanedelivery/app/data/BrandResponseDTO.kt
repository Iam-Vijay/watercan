package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BrandResponseDTO(

	@field:SerializedName("brand")
	val brand: ArrayList<BrandDTO?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)