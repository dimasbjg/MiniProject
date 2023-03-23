package com.dimdimbjg.miniproject.domain.usecase

import androidx.lifecycle.asLiveData
import com.dimdimbjg.miniproject.domain.repository.IRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LaporanInteractor(private val repository: IRepository) : LaporanUseCase {
    override fun getAllLaporan() = repository.getAllLaporan().asLiveData()
    override fun getAllVehicle() = repository.getAllVehicle().asLiveData()
    override fun addLaporan(
        file: MultipartBody.Part,
        vehicleId: RequestBody,
        userId: RequestBody,
        note: RequestBody,
    ) = repository.addLaporan(file,vehicleId,userId,note).asLiveData()
}