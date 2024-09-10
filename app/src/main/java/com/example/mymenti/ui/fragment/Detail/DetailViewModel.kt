package com.example.mymenti.ui.fragment.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.data.local.repository.MentiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MentiRepository): ViewModel() {

    fun getMenti(id: Int) = repository.getMentiByID(id)

}