package com.mobproassesment2.actilog.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobproassesment2.actilog.database.KegiatanDb
import com.mobproassesment2.actilog.ui.screen.DetailViewModel
import com.mobproassesment2.actilog.ui.screen.MainViewModel
import com.mobproassesment2.actilog.ui.screen.RecycleBinViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = KegiatanDb.getInstance(context).dao

        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(dao) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(dao) as T
            }
            modelClass.isAssignableFrom(RecycleBinViewModel::class.java) -> {
                RecycleBinViewModel(dao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
