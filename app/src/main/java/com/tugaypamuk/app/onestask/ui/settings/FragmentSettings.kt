package com.tugaypamuk.app.onestask.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.databinding.FragmentSettingsBinding
import com.tugaypamuk.app.onestask.ui.BaseFragment

import dagger.hilt.android.AndroidEntryPoint
import java.net.Socket

@AndroidEntryPoint
class FragmentSettings : BaseFragment<FragmentSettingsBinding>() {
    private val viewModel by viewModels<SettingsViewModel>()
    override fun getFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentSettingsSaveBtn.setOnClickListener {
            checkEditTexts()
        }

        binding.fragmentSettingsDemoServer.setOnClickListener {
            binding.port.editText?.setText("587")
            binding.stmpAddress.editText?.setText("sandbox.smtp.mailtrap.io")
            binding.username.editText?.setText("77e17e5d02ea8b")
            binding.password.editText?.setText("1a05a8c3a4c935")
            binding.sendto.editText?.setText("15611322d1-e11c54+1@inbox.mailtrap.io")
        }

        viewModel.getMailServer()
        viewModel.mailServerList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.port.editText?.setText(it.port.toString())
                binding.stmpAddress.editText?.setText(it.host.toString())
                binding.username.editText?.setText(it.username.toString())
                binding.password.editText?.setText(it.password.toString())
                binding.sendto.editText?.setText(it.sendFrom.toString())
            }

        }
    }

    private fun checkEditTexts() {
        var anyTextInputEmpty = false
        val textInputLayoutList = arrayOf(
            binding.port,
            binding.password,
            binding.stmpAddress,
            binding.username,
            binding.sendto
        )
        for (textInputLayout in textInputLayoutList) {
            val edittext = textInputLayout.editText
            if (edittext != null && edittext.text.isBlank()) {
                textInputLayout.error = "Bu alan bos birakilamaz"
                anyTextInputEmpty = true
            } else
                textInputLayout.error = null
        }
        if (!anyTextInputEmpty) {
            val port = binding.port.editText?.text.toString()
            val smpt = binding.stmpAddress.editText?.text.toString()
            val username = binding.username.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            val mailTo = binding.sendto.editText?.text.toString()
            viewModel.addMailServer(
                MailSettingsEntity(
                    0,
                    smpt,
                    port,
                    username,
                    password,
                    mailTo
                )
            )

            Toast.makeText(requireContext(), "Sunucu bilgileri kaydedildi", Toast.LENGTH_LONG)
                .show()

        }
    }


}