package com.example.mymenti.ui.fragment.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.data.local.repository.MentiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MentiRepository): ViewModel() {

    private val _mentiStateFlow = MutableStateFlow<MentiModel?>(null)
    val mentiStateFlow: StateFlow<MentiModel?> get() = _mentiStateFlow

    private val jj = MutableStateFlow<MentiModel?>(null)
    val _jj: StateFlow<MentiModel?> get() = jj

    private val hh = MutableStateFlow<String?>(null)
    val _hh: StateFlow<String?> get() = hh

    fun <T> na(n: T) {
        hh.value = n.toString()
    }

    fun getMentiById(id: Int) {
        viewModelScope.launch {
            repository.getMentiByID(id).collect {
                _mentiStateFlow.value = it
            }
        }
    }
}