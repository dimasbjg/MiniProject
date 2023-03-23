package com.dimdimbjg.miniproject.data

import com.dimdimbjg.miniproject.data.source.local.LocalDataSource
import com.dimdimbjg.miniproject.data.source.remote.RemoteDataSource
import com.dimdimbjg.miniproject.data.source.remote.network.ResponseApi
import com.dimdimbjg.miniproject.data.source.remote.response.ListLaporanResponse
import com.dimdimbjg.miniproject.data.source.remote.response.ListVehicleResponse
import com.dimdimbjg.miniproject.data.source.remote.response.TambahResponse
import com.dimdimbjg.miniproject.domain.model.Laporan
import com.dimdimbjg.miniproject.domain.model.Tambah
import com.dimdimbjg.miniproject.domain.model.Vehicle
import com.dimdimbjg.miniproject.domain.repository.IRepository
import com.dimdimbjg.miniproject.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IRepository {
    override fun getAllLaporan(): Flow<Resource<List<Laporan>>> {
        return object : NetworkBoundResource<List<Laporan>, ListLaporanResponse>() {
            override fun populateDataFromDb(): Flow<List<Laporan>> {
                return localDataSource.getAllLaporan().map { Mapper.laporanEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Laporan>?): Boolean {
                return true
            }

            override suspend fun networkCall(): Flow<ResponseApi<ListLaporanResponse>> {
                return remoteDataSource.getAllLaporan()
            }

            override suspend fun saveCallResult(data: ListLaporanResponse) {
                val mappedData = Mapper.laporanResponseToEntities(data)
                localDataSource.insertLaporan(mappedData)
            }
        }.build()
    }

    override fun getAllVehicle(): Flow<Resource<List<Vehicle>>> {
        return object : NetworkBoundResource<List<Vehicle>, ListVehicleResponse>() {
            override fun populateDataFromDb(): Flow<List<Vehicle>> {
                return localDataSource.getAllVehicle().map { Mapper.vehicleEntitiesToDomain(it) }
            }

            override suspend fun networkCall(): Flow<ResponseApi<ListVehicleResponse>> {
                return remoteDataSource.getAllVehicle()
            }

            override suspend fun saveCallResult(data: ListVehicleResponse) {
                val mappedData = Mapper.vehicleResponseToEntities(data)
                localDataSource.insertVehicle(mappedData)
            }

            override fun shouldFetch(data: List<Vehicle>?) = data.isNullOrEmpty()

        }.build()
    }

    override fun addLaporan(
        file: MultipartBody.Part,
        vehicleId: RequestBody,
        userId: RequestBody,
        note: RequestBody,
    ): Flow<Resource<Tambah>> {
        return object : NetworkBoundResource<Tambah, TambahResponse>() {

            override suspend fun networkCall(): Flow<ResponseApi<TambahResponse>> {
                return remoteDataSource.addLaporan(file, vehicleId, userId, note)
            }

            override fun populateDataFromDb(): Flow<Tambah> {
                return localDataSource.getTambah().mapNotNull {
                    if (it != null) {
                        Mapper.tambahEntitiesToDomain(it)
                    }
                    else {
                        Tambah.createEmptyObject()
                    }
                }
            }


            override suspend fun saveCallResult(data: TambahResponse) {
                val mappedData = Mapper.tambahResponseToEntities(data)
                localDataSource.insertTambah(mappedData)
            }

            override fun shouldFetch(data: Tambah?): Boolean {
                return !data!!.status
            }

        }.build()
    }


}