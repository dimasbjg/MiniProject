package com.dimdimbjg.miniproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TambahResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)
