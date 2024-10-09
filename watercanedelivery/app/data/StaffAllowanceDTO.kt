package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StaffAllowanceDTO(
	val id: String? = null,
	@SerializedName("staff_id")
	val staffId: String? = null,
	val date: String? = null,
	val entry_date: String? = null,
	@SerializedName("start_km")
	val startKm: String? = null,
	@SerializedName("end_km")
	val endKm: String? = null,
	@SerializedName("total_km")
	val totalKm: String? = null,
	val createdAt: String? = null,
	val createdBy: String? = null,
	val updatedAt: String? = null
)