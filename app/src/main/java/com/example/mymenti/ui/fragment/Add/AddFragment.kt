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
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private var existingModel: MentiModel? = null
    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()
    private lateinit var selectedCard: MaterialCardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedCard = binding.cardGray
        initializeData()
        setUpListener()
        cardClick()
    }

    private fun initializeData() {
        if (args.mentiID != -1) {
            viewModel.getMentiByID(args.mentiID).observe(viewLifecycleOwner) {
                dataBind(it)
                existingModel = it
            }
        }
    }

    private fun dataBind(mentiModel: MentiModel) = with(binding) {
        etName.setText(mentiModel.name)
        etDescription.setText(mentiModel.description)
        etGroup.setText(mentiModel.group)
        etMonth.setText(mentiModel.month)
        etTelegram.setText(mentiModel.tg)
    }

    private fun setUpListener() = with(binding) {
        btnSave.setOnClickListener {
            if (args.mentiID == -1) {
                insert(false)
            } else {
                insert(true)
            }
        }
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun cardClick() = with(binding) {
        cardGray.setOnClickListener {
            updateSelectedCard(cardGray)
        }
        cardRed.setOnClickListener {
            updateSelectedCard(cardRed)
        }
        cardGreen.setOnClickListener {
            updateSelectedCard(cardGreen)
        }
        cardYellow.setOnClickListener {
            updateSelectedCard(cardYellow)
        }
        cardCold.setOnClickListener {
            updateSelectedCard(cardCold)
        }
    }

    private fun updateSelectedCard(newSelectedCard: MaterialCardView) {
        selectedCard.strokeWidth = 0
        newSelectedCard.strokeWidth = 4
        selectedCard = newSelectedCard
    }

    private fun cardColor(): String = with(binding) {
        when (selectedCard) {
            cardGray -> return "gray"
            cardRed -> return "red"
            cardGreen -> return "green"
            cardYellow -> return "yellow"
            cardCold -> return "cold"
            else -> {
                return "gray"
            }
        }
    }

    private fun insert(isUpdate: Boolean) = with(binding) {
        val name = etName.text.toString()
        val detail = etDescription.text.toString()
        val group = etGroup.text.toString()
        val month = etMonth.text.toString()
        var color = cardColor()
        var telegram = etTelegram.text.toString()
        if (telegram.startsWith("@")) {
            telegram = telegram.substring(1)
        }

        val model = existingModel?.copy(
            name = name,
            description = detail,
            group = group,
            month = month,
            tg = telegram,
            color = color
        ) ?: MentiModel(
            name = name,
            description = detail,
            group = group,
            month = month,
            tg = telegram,
            color = color
        )

        if (isUpdate) {
            viewModel.update(model)
        } else {
            viewModel.insert(model)
        }
        findNavController().navigateUp()
    }
}