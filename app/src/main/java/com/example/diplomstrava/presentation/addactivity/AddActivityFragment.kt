package com.example.diplomstrava.presentation.addactivity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentAddActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddActivityFragment : Fragment(R.layout.fragment_add_activity), AdapterView.OnItemSelectedListener {

    private val binding by viewBinding(FragmentAddActivityBinding::bind)
    private val viewModel: AddActivityViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTypeMenu()

        binding.buttonAdd.setOnClickListener {
            viewModel.saveActivity(
                name = binding.editName.editText?.text.toString(),
                type = "Run", // after add type activity to spinner https://developers.strava.com/docs/reference/#api-models-ActivityType
                date = binding.editDate.editText?.text.toString(),
                time = binding.editTime.editText?.text.toString().toLong(),
                distance = binding.editDistance.editText?.text.toString().toDouble(),
                description = binding.editDescription.editText?.text.toString()
            )
        }

    }

    private fun initTypeMenu() {
       // val items = menuList
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_exposed_menu, R.array.planets_array)
//        binding.spinnerType.adapter = adapter
//        binding.spinnerType.onItemSelectedListener = this
//        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerType.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Timber.d(pos.toString())  //changeWeight in Room Person
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}