package com.dimdimbjg.miniproject.data.source.remote.network

import com.dimdimbjg.miniproject.data.source.remote.response.LaporanResponse
import com.dimdimbjg.miniproject.data.source.remote.response.ListLaporanResponse
import com.dimdimbjg.miniproject.data.source.remote.response.TambahResponse
import com.dimdimbjg.miniproject.data.source.remote.response.VehicleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

const val userId: String = "ILpBZSuKU8"

interface ServiceApi {

    @GET("read_all_laporan?userId=$userId")
    suspend fun getAllLaporan(): List<LaporanResponse>

    @GET("list_vehicle")
    suspend fun getAllVehicle(): List<VehicleResponse>

    @Multipart
    @POST("add_laporan")
    suspend fun addLaporan(
        @Part file: MultipartBody.Part,
        @Part("vehicleId") vehicleId: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part("note") note: RequestBody
    ): TambahResponse

}