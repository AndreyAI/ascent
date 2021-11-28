package com.example.diplomstrava.presentation.addactivity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentAddActivityBinding
import com.example.diplomstrava.utils.FormatData.formatDate
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import timber.log.Timber

@AndroidEntryPoint
class AddActivityFragment : Fragment(R.layout.fragment_add_activity),
    AdapterView.OnItemSelectedListener {

    private val binding by viewBinding(FragmentAddActivityBinding::bind)
    private val viewModel: AddActivityViewModel by viewModels()
    private var instant: Instant = Instant.now()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTypeMenu()

        binding.buttonAdd.setOnClickListener {
            if (validateForm()) {
                viewModel.saveActivity(
                    name = binding.editNameInput.text.toString(),
                    type = binding.spinnerType.selectedItem.toString(), // after add type activity to spinner https://developers.strava.com/docs/reference/#api-models-ActivityType
                    date = instant.toString(),
                    time = binding.editTimeInput.text.toString(),
                    distance = binding.editDistanceInput.text.toString().toDouble(),
                    description = binding.editDescriptionInput.text.toString()
                )
                findNavController().popBackStack()
            }
        }

        binding.editDateInput.setOnClickListener {
            setDateTime()
        }
//        binding.editTimeInput.setOnClickListener {
//            DurationDialog()
//                .show(childFragmentManager, "Duration")
//        }

    }

    private fun initTypeMenu() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            R.layout.item_exposed_menu
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

    private fun setDateTime() {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        instant =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneOffset.UTC).toInstant()
                        Timber.d(instant.toString())
                        binding.editDateInput.setText(formatDate(instant.toString()))
                    },
                    LocalDateTime.ofInstant(
                        instant,
                        ZoneId.systemDefault()
                    ).hour,
                    LocalDateTime.ofInstant(
                        instant,
                        ZoneId.systemDefault()
                    ).minute,
                    true
                )
                    .show()
            },
            LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).year,
            LocalDateTime.ofInstant(
                instant,
                ZoneId.systemDefault()
            ).month.value - 1,
            LocalDateTime.ofInstant(
                instant,
                ZoneId.systemDefault()
            ).dayOfMonth
        )
            .show()
    }

    @SuppressLint("ResourceAsColor")
    private fun validateForm(): Boolean {
        val isNameEmpty = binding.editNameInput.text.toString().isEmpty()
        //val isTypeEmpty = binding.spinnerType.selectedItem.toString().isEmpty()
        val isDateEmpty = binding.editDateInput.text.toString().isEmpty()
        val isTimeEmpty = binding.editTimeInput.text.toString().isEmpty()
        val isDistanceEmpty = binding.editDistanceInput.text.toString().isEmpty()
        //val isDescriptionEmpty = binding.editDescriptionInput.text.toString().isEmpty()

        if (isNameEmpty) binding.editName.error = "You need enter a name"
        else binding.editName.error = null

        if (isDateEmpty) binding.editDate.error = "You need choice a date"
        else binding.editDate.error = null

        if (isTimeEmpty) binding.editTime.error = "You need enter a time"
        else binding.editTime.error = null

        if (isDistanceEmpty) binding.editDistance.error = "You need enter a distance"
        else binding.editDistance.error = null

        return !isNameEmpty && !isDateEmpty && !isTimeEmpty && !isDistanceEmpty

    }

}