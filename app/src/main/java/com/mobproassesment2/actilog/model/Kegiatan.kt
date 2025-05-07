package com.mobproassesment2.actilog.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kegiatan")
data class Kegiatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val catatan: String,
    val tanggal: String
)
