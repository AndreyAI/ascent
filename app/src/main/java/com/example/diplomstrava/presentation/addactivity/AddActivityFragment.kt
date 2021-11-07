package com.example.diplomstrava.presentation.addactivity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentAddActivityBinding

class AddActivityFragment : Fragment(R.layout.fragment_add_activity) {

    private val binding by viewBinding(FragmentAddActivityBinding::bind)
    private val viewModel: AddActivityViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            viewModel.saveActivity(
                name = binding.editName.editText?.text.toString(),
                type = "Run", // after add type activity to spinner https://developers.strava.com/docs/reference/#api-models-ActivityType
                date = binding.editDate.editText?.text.toString(),
                time = binding.editTime.editText?.text.toString(),
                distance = binding.editDistance.editText?.text.toString(),
                description = binding.editDescription.editText?.text.toString()
            )
        }

    }

}