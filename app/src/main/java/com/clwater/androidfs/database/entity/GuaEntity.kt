package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GuaEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val desc_group: String,
    val desc: String,
    val image: String,
    val detail: String
)

