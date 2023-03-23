package com.dimdimbjg.miniproject.data.source.local

import com.dimdimbjg.miniproject.data.source.local.database.LaporanDao
import com.dimdimbjg.miniproject.data.source.local.entities.LaporanEntity
import com.dimdimbjg.miniproject.data.source.local.entities.TambahEntity
import com.dimdimbjg.miniproject.data.source.local.entities.VehicleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource constructor(private val laporanDao: LaporanDao) {
    fun getAllLaporan(): Flow<List<LaporanEntity>> = laporanDao.getAllLaporan()

    fun getAllVehicle(): Flow<List<VehicleEntity>> = laporanDao.getAllVehicle()

    fun getTambah(): Flow<TambahEntity> = laporanDao.getTambah()

    suspend fun insertLaporan(allLaporan: List<LaporanEntity>) {
        withContext(Dispatchers.IO) {laporanDao.insertLaporan(allLaporan)}
    }

    suspend fun insertVehicle(allVehicle: List<VehicleEntity>) {
        withContext(Dispatchers.IO) {laporanDao.insertVehicle(allVehicle)}
    }

    suspend fun insertTambah(tambah: TambahEntity) {
        withContext(Dispatchers.IO) {laporanDao.insertTambah(tambah)}
    }
}