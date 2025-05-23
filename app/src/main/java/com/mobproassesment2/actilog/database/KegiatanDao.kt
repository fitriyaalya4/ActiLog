package com.mobproassesment2.actilog.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mobproassesment2.actilog.model.Kegiatan
import kotlinx.coroutines.flow.Flow

@Dao
interface KegiatanDao {

    @Insert
    suspend fun insert(kegiatan: Kegiatan)

    @Update
    suspend fun update(kegiatan: Kegiatan)

    @Query("SELECT * FROM kegiatan WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getKegiatan(): Flow<List<Kegiatan>>

    @Query("SELECT * FROM kegiatan WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getDeletedKegiatan(): Flow<List<Kegiatan>>

    @Query("SELECT * FROM kegiatan WHERE isDeleted = 0")
    fun getAll(): Flow<List<Kegiatan>>

    @Query("SELECT * FROM kegiatan WHERE id = :id")
    suspend fun getKegiatanById(id: Long): Kegiatan?

    @Query("UPDATE kegiatan SET isDeleted = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long)

    @Query("UPDATE kegiatan SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("DELETE FROM kegiatan WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM kegiatan WHERE isDeleted = 1")
    suspend fun emptyRecycleBin()
}
