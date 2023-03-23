package com.dimdimbjg.miniproject.utils

import com.dimdimbjg.miniproject.data.source.local.entities.LaporanEntity
import com.dimdimbjg.miniproject.data.source.local.entities.TambahEntity
import com.dimdimbjg.miniproject.data.source.local.entities.VehicleEntity
import com.dimdimbjg.miniproject.data.source.remote.response.ListLaporanResponse
import com.dimdimbjg.miniproject.data.source.remote.response.ListVehicleResponse
import com.dimdimbjg.miniproject.data.source.remote.response.TambahResponse
import com.dimdimbjg.miniproject.domain.model.Laporan
import com.dimdimbjg.miniproject.domain.model.Tambah
import com.dimdimbjg.miniproject.domain.model.Vehicle

object Mapper {
    fun laporanEntitiesToDomain(list: List<LaporanEntity>): List<Laporan> {
        return list.map {
            Laporan(
                reportId = it.reportId,
                note = it.note,
                createdAt = it.createdAt,
                vehicleName = it.vehicleName,
                vehicleId = it.vehicleId,
                createdBy = it.createdBy,
                photo = it.photo,
                reportStatus = it.reportStatus,
                vehicleLicenseNumber = it.vehicleLicenseNumber
            )
        }
    }

    fun laporanResponseToEntities(responseList: ListLaporanResponse): List<LaporanEntity> {
        val allLaporan = responseList.laporanResponse
        return allLaporan.map {
            LaporanEntity(
                reportId = it.reportId,
                note = it.note,
                createdAt = it.createdAt,
                vehicleName = it.vehicleName,
                vehicleId = it.vehicleId,
                createdBy = it.createdBy,
                photo = it.photo,
                reportStatus = it.reportStatus,
                vehicleLicenseNumber = it.vehicleLicenseNumber
            )
        }
    }

    fun vehicleEntitiesToDomain(list: List<VehicleEntity>): List<Vehicle> {
        return list.map {
            Vehicle(
                vehicleId = it.vehicleId,
                licenseNumber = it.licenseNumber,
                type = it.type
            )
        }
    }



    fun vehicleResponseToEntities(responseList: ListVehicleResponse): List<VehicleEntity> {
        val allVehicle = responseList.vehicleResponse
        return allVehicle.map {
            VehicleEntity(
                vehicleId = it.vehicleId,
                licenseNumber = it.licenseNumber,
                type = it.type
            )
        }
    }

    fun tambahEntitiesToDomain(tambah: TambahEntity) : Tambah {
        return Tambah(
            status = tambah.status,
            message = tambah.message
        )
    }

    fun tambahResponseToEntities(tambah: TambahResponse): TambahEntity {
        return TambahEntity(
            status = tambah.status,
            message = tambah.message
        )
    }

}