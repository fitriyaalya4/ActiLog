package com.mobproassesment2.actilog.ui.screen

import androidx.lifecycle.ViewModel
import com.mobproassesment2.actilog.model.Kegiatan

class MainViewModel : ViewModel() {
    val data = listOf(
        Kegiatan(
            1,
            "Tugas",
            "Tugas KAT modul 7",
            "19-05-2025 14:30"
        ),
       Kegiatan(
            2,
            "Tugas",
            "Tugas KAT modul 8",
            "19-05-2025 14:30"
        ),
        Kegiatan(
            3,
            "Belanja",
            "Beli Ayam, Cumi, Udang, sayur",
            "19-05-2025 14:30"
        ),
        Kegiatan(
            4,
            "Baca Buku",
            "Hujan halaman 115",
            "19-05-2025 14:30"
        ),
        Kegiatan(
            5,
            "Tugas",
            "Buat Artikel KPL",
            "19-05-2025 14:30"
        ),
        Kegiatan(
            6,
            "Tugas",
            "Jurnal PT Bab 4",
            "19-05-2025 14:30"
        )
    )

    fun getCatatan(id: Long): Kegiatan? {
        return data.find { it.id == id }
    }

}