package com.example.mymenti.ui.fragment.Menti

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.data.local.repository.MentiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MentiViewModel @Inject constructor(private val repository: MentiRepository) : ViewModel() {

    private val _filteredMenti = MutableLiveData<List<MentiModel>>()
    val filteredMenti: LiveData<List<MentiModel>> get() = _filteredMenti


    fun filterMentiByCategory(category: String) {
        when (category) {
            "Имя" -> {
                repository.getMentiByName().observeForever { mentiList ->
                    _filteredMenti.value = mentiList
                }
            }
            "Месяц" -> {
                repository.getMentiByMonth().observeForever { mentiList ->
                    _filteredMenti.value = mentiList
                }
            }
            "Группа" -> {
                repository.getMentiByGroup().observeForever { mentiList ->
                    _filteredMenti.value = mentiList
                }
            }
            else -> {
                repository.getMentiByMonth().observeForever { mentiList ->
                    _filteredMenti.value = mentiList
                }
            }
        }
    }

    fun delete(mentiModel: MentiModel) =
        viewModelScope.launch { repository.deleteMenti(mentiModel) }
}
