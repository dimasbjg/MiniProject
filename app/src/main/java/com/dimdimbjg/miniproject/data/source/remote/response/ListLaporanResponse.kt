package com.dimdimbjg.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListLaporanResponse(
    @SerializedName("results")
    val laporanResponse: List<LaporanResponse> = listOf()
)
