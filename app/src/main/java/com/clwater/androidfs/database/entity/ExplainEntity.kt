package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExplainEntity(
    @PrimaryKey val id: Int,
    val gua_id: Int,
    val explainType: Int,
    val mainExplain: String,
    val base: String
)
