package com.example.diplomstrava.presentation.person

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.diplomstrava.R
import com.example.diplomstrava.data.Person
import com.example.diplomstrava.databinding.FragmentPersonBinding
import com.example.diplomstrava.utils.FormatData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_person) {

    private val binding by viewBinding(FragmentPersonBinding::bind)
    private val viewModel: PersonViewModel by viewModels()
    private val menuList = generateWeightList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initExposedMenu()
        viewModel.bindViewModel()
        viewModel.person.observe(viewLifecycleOwner) {
            if (it != null) //first launch if db is empty
                bindViewModel(it)
        }

    }

    private fun bindViewModel(person: Person) {
        Glide.with(binding.imageAvatar)
            .load(person.avatarUrl)
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_account)
            .into(binding.imageAvatar)

        binding.textName.text = "${person.firstName} ${person.lastName}"
        binding.textMail.text = FormatData.formatDate(person.lastUpdate)
        binding.textFollowingCount.text = person.followingCount.toString()
        binding.textFollowersCount.text = person.followersCount.toString()
        binding.textGender.text = when (binding.textGender.text) {
            "F" -> "Female"
            else -> "Mail"
        }
        binding.textCountry.text = person.country
        binding.textSpinner.setText(menuList[person.weight.toInt() - 10], false)

    }

    private fun initExposedMenu() {
        val items = menuList
        val adapter = ArrayAdapter(requireContext(), R.layout.item_exposed_menu, items)
        (binding.spinnerWeight.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun generateWeightList(): List<String> {
        val list = mutableListOf<String>()
        (10..350).forEach { list.add("$it kg") }
        return list
    }
}