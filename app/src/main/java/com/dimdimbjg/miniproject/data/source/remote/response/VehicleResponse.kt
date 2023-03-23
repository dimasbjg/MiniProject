package com.dimdimbjg.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VehicleResponse(

	@field:SerializedName("licenseNumber")
	val licenseNumber: String,

	@field:SerializedName("vehicleId")
	val vehicleId: String,

	@field:SerializedName("type")
	val type: String
)
