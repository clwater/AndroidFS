package com.clwater.androidfs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clwater.androidfs.database.entity.GuaEntity

@Dao
interface GuaDao {
    @Query("SELECT * FROM GuaEntity")
    fun getAll(): List<GuaEntity>

    @Insert
    fun insertAll(vararg guaEntity: GuaEntity)

    @Query("DELETE FROM GuaEntity")
    fun deleteAll()

}