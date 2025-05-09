package com.mobproassesment2.actilog.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobproassesment2.actilog.database.KegiatanDao
import com.mobproassesment2.actilog.model.Kegiatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecycleBinViewModel(private val dao: KegiatanDao) : ViewModel() {

    val deletedData: StateFlow<List<Kegiatan>> = dao.getDeletedKegiatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
    fun restoreKegiatan(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreById(id)
        }
    }
    fun permanentDeleteKegiatan(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
    fun emptyRecycleBin() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.emptyRecycleBin()
        }
    }
}
