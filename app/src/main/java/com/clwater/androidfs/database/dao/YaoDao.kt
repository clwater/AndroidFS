package com.clwater.androidfs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clwater.androidfs.database.entity.YaoEntity

@Dao
interface YaoDao {
    @Query("SELECT * FROM YaoEntity")
    fun getAll(): List<YaoEntity>

    @Insert
    fun insertAll(vararg yaoEntity: YaoEntity)

    @Query("DELETE FROM YaoEntity")
    fun deleteAll()
}