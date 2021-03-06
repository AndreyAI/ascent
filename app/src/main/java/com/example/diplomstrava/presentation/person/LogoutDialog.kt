package com.example.diplomstrava.presentation.person

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplomstrava.databinding.DialogLogoutBinding
import timber.log.Timber

class LogoutDialog : DialogFragment() {

    private var _binding: DialogLogoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogLogoutBinding.inflate(LayoutInflater.from(context))

        binding.buttonNo.setOnClickListener {
            dismiss()
        }

        binding.buttonYes.setOnClickListener {
            Timber.d("Yes dialog")
            (parentFragment as Logout).logout()
            dismiss()
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    interface Logout {
        fun logout()
    }
}