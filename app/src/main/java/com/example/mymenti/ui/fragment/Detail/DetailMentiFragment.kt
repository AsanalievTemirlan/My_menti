package com.example.mymenti.ui.fragment.Detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mymenti.databinding.FragmentDetailMentiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMentiFragment : Fragment() {

    private lateinit var binding: FragmentDetailMentiBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailMentiFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMentiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        initListener()
    }

    private fun initListener() {
        binding.btnChange.setOnClickListener {
            val action =
                DetailMentiFragmentDirections.actionDetailMentiFragmentToAddFragment(args.mentiID)
            findNavController().navigate(action)
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvTelegram.setOnClickListener {
            viewModel.getMenti(args.mentiID).observe(viewLifecycleOwner){
                val telegramUsername = it.tg
                openTelegram(telegramUsername)
            }
        }

    }


    private fun addData() = with(binding) {
        viewModel.getMenti(args.mentiID).observe(viewLifecycleOwner){
            tvName.text = "Имя: ${it.name}"
            tvDescription.text = "Описание: ${it.description}"
            tvGroup.text = "Группа: ${it.group}"
            tvMonth.text = "Месяц: ${it.month}"
        })
    }

    private fun openTelegram(username: String) {
        val url = "tg://resolve?domain=$username"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            setPackage("org.telegram.messenger")
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Telegram is not installed on your device",
                Toast.LENGTH_SHORT
            ).show()

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=org.telegram.messenger")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                    )
                )
            }

        }
    }
}