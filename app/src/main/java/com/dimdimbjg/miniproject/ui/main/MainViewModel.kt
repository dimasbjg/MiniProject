package com.dimdimbjg.miniproject.ui.main

import androidx.lifecycle.ViewModel
import com.dimdimbjg.miniproject.domain.usecase.LaporanUseCase

class MainViewModel constructor(
    private val useCase: LaporanUseCase,
) : ViewModel() {

    fun getAllLaporan() = useCase.getAllLaporan()


}