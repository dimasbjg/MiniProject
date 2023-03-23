package com.dimdimbjg.miniproject.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dimdimbjg.miniproject.data.source.local.entities.LaporanEntity
import com.dimdimbjg.miniproject.data.source.local.entities.TambahEntity
import com.dimdimbjg.miniproject.data.source.local.entities.VehicleEntity
import com.dimdimbjg.miniproject.domain.model.Tambah
import kotlinx.coroutines.flow.Flow

@Dao
interface LaporanDao {
    @Query("SELECT * FROM allLaporan")
    fun getAllLaporan(): Flow<List<LaporanEntity>>

    @Query("SELECT * FROM allVehicle")
    fun getAllVehicle(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM tambah")
    fun getTambah(): Flow<TambahEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaporan(allLaporan: List<LaporanEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVehicle(allVehicle: List<VehicleEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTambah(tambah: TambahEntity)

}