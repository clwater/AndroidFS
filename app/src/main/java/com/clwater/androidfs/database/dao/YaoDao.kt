package com.clwater.androidfs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clwater.androidfs.database.entity.Yao

@Dao
interface YaoDao {
    @Query("SELECT * FROM Yao")
    fun getAll(): List<Yao>

    @Insert
    fun insertAll(vararg yao: Yao)

}