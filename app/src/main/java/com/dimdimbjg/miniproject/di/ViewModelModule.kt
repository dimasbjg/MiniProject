package com.dimdimbjg.miniproject.di

import com.dimdimbjg.miniproject.ui.addlaporan.AddLaporanViewModel
import com.dimdimbjg.miniproject.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AddLaporanViewModel(get()) }
}