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
    val explain_0_origin: String,
    val explain_0_explain: String,
    val explain_1_origin: String,
    val explain_1_explain: String,
    val explain_2_origin: String,
    val explain_2_explain: String,
    val philosophy: String
)
