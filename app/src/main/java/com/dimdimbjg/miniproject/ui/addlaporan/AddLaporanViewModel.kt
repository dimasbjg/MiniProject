package com.dimdimbjg.miniproject.ui.addlaporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dimdimbjg.miniproject.data.Resource
import com.dimdimbjg.miniproject.domain.model.Tambah
import com.dimdimbjg.miniproject.domain.usecase.LaporanUseCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddLaporanViewModel constructor(
    private val usecase: LaporanUseCase,
) : ViewModel() {

    fun getAllVehicle() = usecase.getAllVehicle()

    fun addLaporan(
        file: File,
        vehicleId: String,
        userId: String,
        note: String,
    ): LiveData<Resource<Tambah>> {
        val requestFile = RequestBody.create("file".toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val vehicleIdPart = vehicleId.toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdPart = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val notePart = note.toRequestBody("text/plain".toMediaTypeOrNull())

        return usecase.addLaporan(filePart, vehicleIdPart, userIdPart, notePart)
    }



}
