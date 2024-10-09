package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StaffExpenseDTO(
	val id: String? = null,
	val staffId: String? = null,
	val remarks: String? = null,
	val entry_date: String? = null,
	val amount: String? = null,
	val createdAt: String? = null,

	val createdBy: String? = null,
	@SerializedName("updated_at")
	val updatedAt: String? = null
)