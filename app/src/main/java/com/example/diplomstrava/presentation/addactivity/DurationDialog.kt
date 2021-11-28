package com.example.diplomstrava.presentation.addactivity

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplomstrava.databinding.DialogDurationtBinding
import timber.log.Timber


class DurationDialog : DialogFragment() {

    private var _binding: DialogDurationtBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDurationtBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Ok") { _, _ ->
                val hour = binding.editHoursInput.text.toString()
                val minutes = binding.editMinutesInput.text.toString()
                val text = "$hour h $minutes m"
                Timber.d(text)
                (parentFragment as SetText).setText(text)
            }
            .create()

    }

    interface SetText {
        fun setText(text: String)
    }

}