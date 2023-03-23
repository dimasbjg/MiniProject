package com.dimdimbjg.miniproject.domain.model

data class Laporan(
    val reportId: String,
    val note: String,
    val createdAt: String,
    val vehicleName: String,
    val vehicleLicenseNumber: String,
    val createdBy: String,
    val photo: String,
    val reportStatus: String,
    val vehicleId: String
)
