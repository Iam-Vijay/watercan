package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StaffResponseDTO(
	val status: Boolean? = null,
	val msg: String? = null,
	@SerializedName("staff_expense")
	val staffExpense: ArrayList<StaffExpenseDTO?>? = null
)