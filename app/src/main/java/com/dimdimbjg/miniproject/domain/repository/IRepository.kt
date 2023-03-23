package com.dimdimbjg.miniproject.domain.repository

import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.domain.model.Laporan
import com.dimdimbjg.miniproject.domain.model.Tambah
import com.dimdimbjg.miniproject.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IRepository {
    fun getAllLaporan(): Flow<Resource<List<Laporan>>>
    fun getAllVehicle(): Flow<Resource<List<Vehicle>>>
    fun addLaporan(
        file: MultipartBody.Part,
        vehicleId: RequestBody,
        userId: RequestBody,
        note: RequestBody,
    ): Flow<Resource<Tambah>>
}