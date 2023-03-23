package com.dimdimbjg.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanResponse(
	@field:SerializedName("reportId")
	val reportId: String,

	@field:SerializedName("note")
	val note: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("vehicleName")
	val vehicleName: String,

	@field:SerializedName("vehicleLicenseNumber")
	val vehicleLicenseNumber: String,

	@field:SerializedName("createdBy")
	val createdBy: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("reportStatus")
	val reportStatus: String,

	@field:SerializedName("vehicleId")
	val vehicleId: String
)