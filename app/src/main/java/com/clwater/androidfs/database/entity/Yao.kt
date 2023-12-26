package com.clwater.androidfs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Yao(
    @PrimaryKey val id: Int,
    val name: String,
)

