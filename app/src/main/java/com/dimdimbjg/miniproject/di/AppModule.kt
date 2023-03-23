package com.dimdimbjg.miniproject.di

import com.dimdimbjg.miniproject.data.Repository
import com.dimdimbjg.miniproject.data.source.local.LocalDataSource
import com.dimdimbjg.miniproject.data.source.remote.RemoteDataSource
import com.dimdimbjg.miniproject.domain.repository.IRepository
import com.dimdimbjg.miniproject.domain.usecase.LaporanInteractor
import com.dimdimbjg.miniproject.domain.usecase.LaporanUseCase
import org.koin.dsl.module

val appModule = module {
    single<IRepository> { Repository(get(), get()) }

    single { RemoteDataSource(get()) }

    single { LocalDataSource(get()) }

    single<LaporanUseCase> { LaporanInteractor(get()) }
}