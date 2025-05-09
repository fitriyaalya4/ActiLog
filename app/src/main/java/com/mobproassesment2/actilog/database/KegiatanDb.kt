package com.mobproassesment2.actilog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobproassesment2.actilog.model.Kegiatan

@Database(entities = [Kegiatan::class], version = 1, exportSchema = false)
abstract class KegiatanDb : RoomDatabase() {
    abstract val dao: KegiatanDao

    companion object {
        @Volatile
        private var INSTANCE: KegiatanDb? = null

        fun getInstance(context: Context): KegiatanDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KegiatanDb::class.java,
                        "kegiatan.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
