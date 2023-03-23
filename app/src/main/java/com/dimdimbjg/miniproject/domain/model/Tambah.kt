package com.dimdimbjg.miniproject.domain.model

data class Tambah(
    val message: String,
    val status: Boolean
) {
    companion object {
        fun createEmptyObject(): Tambah = Tambah(message = "kosong", status = false)
    }
}
