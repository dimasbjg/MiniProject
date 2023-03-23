package com.dimdimbjg.miniproject.di

import androidx.room.Room
import com.dimdimbjg.miniproject.config.Constants
import com.dimdimbjg.miniproject.data.source.local.database.LaporanDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single {
        Room.databaseBuilder(androidContext(), LaporanDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<LaporanDatabase>().laporanDao()
    }
}