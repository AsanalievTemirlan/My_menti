package com.example.mymenti.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MentiModel")
data class MentiModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val group: String,
    val month: String,
    val description: String,
    val tg: String
)
