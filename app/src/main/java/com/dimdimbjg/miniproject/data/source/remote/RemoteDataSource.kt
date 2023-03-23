package com.dimdimbjg.miniproject.data.source.remote

import com.dimdimbjg.miniproject.data.source.remote.network.ResponseApi
import com.dimdimbjg.miniproject.data.source.remote.network.ServiceApi
import com.dimdimbjg.miniproject.data.source.remote.response.*
import com.dimdimbjg.miniproject.utils.ErrorMessageHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource constructor(private val serviceApi: ServiceApi) {

    suspend fun getAllLaporan(): Flow<ResponseApi<ListLaporanResponse>> {
        return flow {
            try {
                //mapping to list of object
                val listLaporan = mutableListOf<LaporanResponse>()
                for (laporan in serviceApi.getAllLaporan()) {
                    listLaporan.add(
                        LaporanResponse(
                            reportId = laporan.reportId,
                            note = laporan.note,
                            createdAt = laporan.createdAt,
                            vehicleName = laporan.vehicleName,
                            vehicleId = laporan.vehicleId,
                            createdBy = laporan.createdBy,
                            photo = laporan.photo,
                            reportStatus = laporan.reportStatus,
                            vehicleLicenseNumber = laporan.vehicleLicenseNumber
                        )
                    )
                }

                val data = ListLaporanResponse(listLaporan)

                if (data.laporanResponse.isEmpty()) {
                    emit(ResponseApi.Empty)
                } else {
                    emit(ResponseApi.Success(data))
                }
            } catch (error: Exception) {
                emit(ResponseApi.Error(ErrorMessageHandler.generateErrorMessage(error)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllVehicle(): Flow<ResponseApi<ListVehicleResponse>> {
        return flow {
            try {
                //mapping array to list
                val listVehicle = mutableListOf<VehicleResponse>()
                for (vehicle in serviceApi.getAllVehicle()) {
                    listVehicle.add(
                        VehicleResponse(
                            licenseNumber = vehicle.licenseNumber,
                            vehicleId = vehicle.vehicleId,
                            type = vehicle.type
                        )
                    )
                }

                val data = ListVehicleResponse(listVehicle)

                if (data.vehicleResponse.isEmpty()) {
                    emit(ResponseApi.Empty)
                } else {
                    emit(ResponseApi.Success(data))
                }
            } catch (error: java.lang.Exception) {
                emit(ResponseApi.Error(ErrorMessageHandler.generateErrorMessage(error)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addLaporan(
        file: MultipartBody.Part,
        vehicleId: RequestBody,
        userId: RequestBody,
        note: RequestBody,
    ): Flow<ResponseApi<TambahResponse>> {
        return flow {
            try {
                val data = serviceApi.addLaporan(file, vehicleId, userId, note)
                if (data.status) {
                    emit(ResponseApi.Empty)
                } else {
                    emit(ResponseApi.Success(data))
                }
            } catch (error: Exception) {
                emit(ResponseApi.Error(ErrorMessageHandler.generateErrorMessage(error)))

            }
        }.flowOn(Dispatchers.IO)
    }

}