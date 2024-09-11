package com.example.mymenti.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymenti.data.local.dao.MentiDao
import com.example.mymenti.data.local.model.MentiModel

@Database(entities = [MentiModel::class], version = 3)
abstract class MentiDataBase: RoomDatabase() {
    abstract fun mentiDao(): MentiDao
}