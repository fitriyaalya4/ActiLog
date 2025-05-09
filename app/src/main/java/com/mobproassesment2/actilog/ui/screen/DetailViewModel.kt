package com.mobproassesment2.actilog.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobproassesment2.actilog.database.KegiatanDao
import com.mobproassesment2.actilog.model.Kegiatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KegiatanDao) : ViewModel() {

    var recentlyDeletedKegiatan: Kegiatan? = null

    suspend fun getKegiatan(id: Long): Kegiatan? {
        return dao.getKegiatanById(id)
    }

    fun insert(judul: String, isi: String, tanggal: String) {
        val kegiatan = Kegiatan(
            tanggal = tanggal,
            judul = judul,
            catatan = isi,
            isDeleted = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(kegiatan)
        }
    }

    fun update(id: Long, judul: String, isi: String, tanggal: String) {
        val kegiatan = Kegiatan(
            id = id,
            tanggal = tanggal,
            judul = judul,
            catatan = isi,
            isDeleted = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(kegiatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val kegiatan = dao.getKegiatanById(id)
            kegiatan?.let {
                recentlyDeletedKegiatan = it.copy(isDeleted = true)
                dao.softDeleteById(id)
            }
        }
    }

    fun restoreDeletedKegiatan() {
        viewModelScope.launch(Dispatchers.IO) {
            recentlyDeletedKegiatan?.let {
                dao.update(it.copy(isDeleted = false))
                recentlyDeletedKegiatan = null
            }
        }
    }
}
