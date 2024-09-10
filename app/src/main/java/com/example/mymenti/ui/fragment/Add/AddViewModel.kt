package com.example.mymenti.ui.fragment.Add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.data.local.repository.MentiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: MentiRepository) : ViewModel() {

    fun getMentiByID(id: Int) = repository.getMentiByID(id)

    fun insert(mentiModel: MentiModel) = viewModelScope.launch {
        repository.insertMenti(mentiModel)
    }

    fun update(mentiModel: MentiModel) = viewModelScope.launch {
        repository.updateMenti(mentiModel)
    }
}