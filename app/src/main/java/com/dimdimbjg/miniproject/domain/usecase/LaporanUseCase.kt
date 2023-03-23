package com.dimdimbjg.miniproject.domain.usecase

import androidx.lifecycle.LiveData
import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.domain.model.Laporan
import com.dimdimbjg.miniproject.domain.model.Tambah
import com.dimdimbjg.miniproject.domain.model.Vehicle
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface LaporanUseCase {
    fun getAllLaporan(): LiveData<Resource<List<Laporan>>>
    fun getAllVehicle(): LiveData<Resource<List<Vehicle>>>
    fun addLaporan(
        file: MultipartBody.Part,
        vehicleId: RequestBody,
        userId: RequestBody,
        note: RequestBody
    ): LiveData<Resource<Tambah>>
}