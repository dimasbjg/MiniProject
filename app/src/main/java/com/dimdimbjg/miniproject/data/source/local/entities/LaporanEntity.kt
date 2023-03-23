package com.dimdimbjg.miniproject.data.source.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "allLaporan",indices = [Index(value = ["reportId", "note","createdAt", "vehicleName","vehicleLicenseNumber","createdBy","photo","reportStatus","vehicleId"], unique = true)])
//@Entity(indices = {Index(value = {"first_name", "last_name"}, unique = true)})
data class LaporanEntity(
    @PrimaryKey
    val reportId: String,
    val note: String,
    val createdAt: String,
    val vehicleName: String,
    val vehicleLicenseNumber: String,
    val createdBy: String,
    val photo: String,
    val reportStatus: String,
    val vehicleId: String,
)
