package com.mobproassesment2.actilog.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobproassesment2.actilog.database.KegiatanDao
import com.mobproassesment2.actilog.model.Kegiatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: KegiatanDao) : ViewModel() {

    val data: StateFlow<List<Kegiatan>> = dao.getKegiatan()
        .stateIn(
            scope = viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = emptyList()
        )

}
