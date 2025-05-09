package com.mobproassesment2.actilog.navigation

import com.mobproassesment2.actilog.ui.screen.KEY_ID_KEGIATAN

sealed class Screen(val route: String){
    data object  Home: Screen("mainScreen")
    data object  FormBaru: Screen("detailScreen")
    data object  FormUbah: Screen("detailScreen/{$KEY_ID_KEGIATAN}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object TempatKegiatanDibuang: Screen("Recycle_bin")
}