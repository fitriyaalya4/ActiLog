package com.mobproassesment2.actilog.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobproassesment2.actilog.database.KegiatanDb
import com.mobproassesment2.actilog.ui.screen.DetailViewModel
import com.mobproassesment2.actilog.ui.screen.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = KegiatanDb.getInstance(context).dao
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModelclass")
    }
}
