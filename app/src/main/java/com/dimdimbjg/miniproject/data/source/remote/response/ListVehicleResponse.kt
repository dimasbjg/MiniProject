package com.dimdimbjg.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListVehicleResponse(
    @SerializedName("results")
    val vehicleResponse: List<VehicleResponse> = listOf()
)
