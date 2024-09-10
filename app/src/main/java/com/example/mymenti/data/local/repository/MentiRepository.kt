package com.example.mymenti.data.local.repository

import androidx.lifecycle.LiveData
import com.example.mymenti.data.local.dao.MentiDao
import com.example.mymenti.data.local.model.MentiModel
import javax.inject.Inject

class MentiRepository @Inject constructor(private val dao: MentiDao) {

    fun getAllMenti(): LiveData<List<MentiModel>> = dao.getAllMenti()

    suspend fun insertMenti(menti: MentiModel) = dao.insertMenti(menti)

    suspend fun deleteMenti(menti: MentiModel) = dao.deleteMenti(menti)

    suspend fun updateMenti(menti: MentiModel) = dao.updateMenti(menti)

    fun getMentiByName() = dao.getMentiByName()

    fun getMentiByMonth() = dao.getMentiByMonth()

    fun getMentiByGroup() = dao.getMentiByGroup()

    fun getMentiByID(id: Int) = dao.getMentiById(id)
}