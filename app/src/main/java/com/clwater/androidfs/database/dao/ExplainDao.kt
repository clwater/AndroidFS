package com.clwater.androidfs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clwater.androidfs.database.entity.ExplainEntity


@Dao
interface ExplainDao {
    @Query("SELECT * FROM ExplainEntity")
    fun getAll(): List<ExplainEntity>

    @Insert
    fun insertAll(vararg explainEntity: ExplainEntity)

    @Query("DELETE FROM ExplainEntity")
    fun deleteAll()
}