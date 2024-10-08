package com.example.mymenti.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mymenti.data.local.model.MentiModel

@Dao
interface MentiDao {

    @Insert
    suspend fun insertMenti(menti: MentiModel)

    @Update
    suspend fun updateMenti(menti: MentiModel)

    @Delete
    suspend fun deleteMenti(menti: MentiModel)

    @Query("SELECT * FROM MentiModel")
    fun getAllMenti(): LiveData<List<MentiModel>>

    @Query("SELECT * FROM MentiModel WHERE id = :id")
    fun getMentiById(id: Int): LiveData<MentiModel>

    @Query("SELECT * FROM MentiModel ORDER BY name ASC ")
    fun getMentiByName(): LiveData<List<MentiModel>>

    @Query("SELECT * FROM MentiModel ORDER BY month ASC ")
    fun getMentiByMonth(): LiveData<List<MentiModel>>

    @Query("SELECT * FROM MentiModel ORDER BY `group` ASC ")
    fun getMentiByGroup(): LiveData<List<MentiModel>>
}
