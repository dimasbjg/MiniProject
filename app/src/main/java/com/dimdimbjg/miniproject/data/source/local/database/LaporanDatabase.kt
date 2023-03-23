package com.dimdimbjg.miniproject.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dimdimbjg.miniproject.data.source.local.entities.LaporanEntity
import com.dimdimbjg.miniproject.data.source.local.entities.TambahEntity
import com.dimdimbjg.miniproject.data.source.local.entities.VehicleEntity

@Database(entities = [LaporanEntity::class, VehicleEntity::class, TambahEntity::class], version = 3, exportSchema = false)
abstract class LaporanDatabase : RoomDatabase() {
    abstract fun laporanDao(): LaporanDao
}