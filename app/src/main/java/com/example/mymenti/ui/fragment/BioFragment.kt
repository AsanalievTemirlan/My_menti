package com.example.mymenti.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mymenti.R
import com.example.mymenti.databinding.FragmentBioBinding

class BioFragment : Fragment() {

    private val binding by lazy {
        FragmentBioBinding.inflate(layoutInflater)
    }

    companion object {
        private const val REQUEST_CODE_BIOMETRIC = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Запрашиваем разрешение на биометрию, если его нет
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.USE_BIOMETRIC),
                REQUEST_CODE_BIOMETRIC
            )
        } else {
            // Разрешение уже есть, продолжим с аутентификацией
            authenticateWithBiometrics()
        }


    }

    private fun authenticateWithBiometrics() {
        val executor = ContextCompat.getMainExecutor(requireContext())

        // Используем BiometricPrompt из androidx.biometric
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireContext(),
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.allMentiFragment)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for MyApp")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        binding.btn.setOnClickListener {
            // Запускаем биометрическую аутентификацию
            biometricPrompt.authenticate(promptInfo)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_BIOMETRIC -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Разрешение получено
                    authenticateWithBiometrics()
                } else {
                    // Разрешение не получено
                    Toast.makeText(
                        requireContext(),
                        "Biometric permission is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}