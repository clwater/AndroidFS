package com.clwater.androidfs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clwater.androidfs.database.entity.ExplainItemEntity

@Dao
interface ExplainItemDao {
    @Query("SELECT * FROM ExplainItemEntity")
    fun getAll(): List<ExplainItemEntity>

    @Query("SELECT * FROM ExplainItemEntity WHERE explain_id = :explainId")
    fun getByExplainId(explainId: Int): List<ExplainItemEntity>

    @Insert
    fun insertAll(vararg explainItemEntity: ExplainItemEntity)

    @Query("DELETE FROM ExplainItemEntity")
    fun deleteAll()
}