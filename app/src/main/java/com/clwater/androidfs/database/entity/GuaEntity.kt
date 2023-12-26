package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GuaEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val desc_group: String,
    val desc: String,
    val image: String
)

