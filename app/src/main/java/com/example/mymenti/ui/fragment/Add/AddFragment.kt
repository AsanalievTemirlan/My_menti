package com.example.mymenti.ui.fragment.Add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private var existingModel: MentiModel? = null
    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
        setUpListener()
    }

    private fun initializeData(){
        if(args.mentiID!= -1){
            viewModel.getMentiByID(args.mentiID).observe(viewLifecycleOwner){
                dataBind(it)
                existingModel = it
            }
        }
    }
    private fun dataBind(mentiModel: MentiModel) = with(binding){
        etName.setText(mentiModel.name)
        etDescription.setText(mentiModel.description)
        etGroup.setText(mentiModel.group)
        etMonth.setText(mentiModel.month)
        etTelegram.setText(mentiModel.tg)
    }

    private fun setUpListener() = with(binding) {
        btnSave.setOnClickListener {
            if(args.mentiID == -1){
                insert(false)
            }
            else{
                insert(true)
            }
        }
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun insert(isUpdate: Boolean) = with(binding) {
        val name = etName.text.toString()
        val detail = etDescription.text.toString()
        val group = etGroup.text.toString()
        val month = etMonth.text.toString()
        var telegram = etTelegram.text.toString()

        if (telegram.startsWith("@")) {
            telegram = telegram.substring(1) // Перезаписываем строку без '@'
        }

        val model = existingModel?.copy(
            name = name,
            description = detail,
            group = group,
            month = month,
            tg = telegram
        ) ?: MentiModel(
            name = name,
            description = detail,
            group = group,
            month = month,
            tg = telegram
        )

        if (isUpdate) {
            viewModel.update(model)
        } else {
            viewModel.insert(model)
        }
        findNavController().navigateUp()
    }
}