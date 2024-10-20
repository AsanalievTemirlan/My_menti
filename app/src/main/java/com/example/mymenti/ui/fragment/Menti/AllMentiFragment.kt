package com.example.mymenti.ui.fragment.Menti

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hw_03_m7.ui.medicines.MentiAdapter
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.databinding.FragmentAllMentiBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllMentiFragment : Fragment(), OnClick {

    private lateinit var binding: FragmentAllMentiBinding
    private val viewModel: MentiViewModel by viewModels()
    private lateinit var adapter: MentiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMentiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupRecyclerView()
        initListener()

        val stateFlow = MutableStateFlow("state")

        // В Activity или Fragment
        lifecycleScope.launch {
            stateFlow.collect { newValue ->
                // Обновление UI
            }
        }

    }

    private fun initListener() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(
                AllMentiFragmentDirections.actionAllMentiFragmentToAddFragment(
                    -1
                )
            )
        }
    }

    private fun setupSpinner() {
        val categories = listOf("Имя", "Месяц", "Группа", "Все")
        val adapterS =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.customSpinner.adapter = adapterS

        binding.customSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                // Retrieve the value for filtering (e.g., from an EditText)
                Log.e("ololo", "onItemSelected: $selectedCategory")
                viewModel.filterMentiByCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupRecyclerView() = with(binding) {
        viewModel.filteredMenti.observe(viewLifecycleOwner) { mentiList ->
            Log.e("ololo", "setupRecyclerView: $mentiList ")
            adapter = MentiAdapter(this@AllMentiFragment, this@AllMentiFragment)
            rvMenti.adapter = adapter
            adapter.submitList(mentiList)
        }
    }

    private fun getFilterValue(category: String): Any {
        return when (category) {
            "name" -> "Default Name"
            "month" -> 1 // Example month
            "group" -> "Default Group"
            else -> ""
        }
    }

    override fun onClick(mentiId: MentiModel) {
        val action =
            AllMentiFragmentDirections.actionAllMentiFragmentToDetailMentiFragment(mentiId.id)
        findNavController().navigate(action)
    }

    override fun onLongClick(mentiId: MentiModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Удалить менти")
            setMessage("Удалить менти?")
            setPositiveButton("Да") { dialog, which ->
                viewModel.delete(mentiId)
            }
            setNegativeButton("Нет") { dialog, which ->
                dialog.dismiss()
            }
            show()
        }
    }

}