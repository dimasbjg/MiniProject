package com.dimdimbjg.miniproject.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tambah")
data class TambahEntity (
    @PrimaryKey
    val message: String,
    val status: Boolean
)