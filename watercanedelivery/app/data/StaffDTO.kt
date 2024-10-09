package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StaffDTO(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("staff_id")
	val staffId: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mobile_no")
	val mobileNo: String? = null,

	@field:SerializedName("doj")
	val doj: String? = null,

	@field:SerializedName("last_login")
	val lastLogin: String? = null,

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