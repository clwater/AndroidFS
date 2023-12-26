package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class YaoEntity(
    @PrimaryKey val id: Int,
    val gua_id: Int,
    val index: Int,
    val image: String,
    val base: String,
    val explain_0: String,
    val explain_1: String,
    val explain_2: String,
    val philosophy: String
)
