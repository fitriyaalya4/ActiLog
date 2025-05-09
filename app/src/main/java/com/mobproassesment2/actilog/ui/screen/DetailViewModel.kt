package com.mobproassesment2.actilog.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobproassesment2.actilog.database.KegiatanDao
import com.mobproassesment2.actilog.model.Kegiatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KegiatanDao) : ViewModel() {

    fun insert(judul: String, isi: String, tanggal: String) {
        val catatan = Kegiatan(
            tanggal = tanggal,
            judul = judul,
            catatan = isi,
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    suspend fun getCatatan(id: Long): Kegiatan? {
        return dao.getKegiatanById(id)
    }

    fun update(id: Long, judul: String, isi: String, tanggal: String) {
        val catatan = Kegiatan(
            id = id,
            tanggal = tanggal,
            judul = judul,
            catatan = isi,
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
