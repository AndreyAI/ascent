package com.example.diplomstrava.presentation.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplomstrava.databinding.DialogExitBinding

class ExitDialog : DialogFragment() {

    private var _binding: DialogExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogExitBinding.inflate(LayoutInflater.from(context))

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonExit.setOnClickListener {
            (activity as MainActivity).exit()
            dismiss()
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    interface Exit{
        fun exit()
    }
}