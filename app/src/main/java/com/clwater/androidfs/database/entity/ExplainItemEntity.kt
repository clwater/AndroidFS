package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ExplainItemEntity(
    @PrimaryKey val id: Int,
    val gua_id: Int,
    val explainId: Int,
    val index: Int,
    val explain: String
)
