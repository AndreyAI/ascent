package com.example.diplomstrava.presentation.person

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.diplomstrava.R
import com.example.diplomstrava.data.Person
import com.example.diplomstrava.databinding.FragmentPersonBinding
import com.example.diplomstrava.presentation.ScreenState
import com.example.diplomstrava.presentation.containerfragment.ContainerFragmentDirections
import com.example.diplomstrava.utils.FormatData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_person), AdapterView.OnItemSelectedListener,
    LogoutDialog.Logout {

    private val binding by viewBinding(FragmentPersonBinding::bind)
    private val viewModel: PersonViewModel by viewModels()
    private val menuList = generateWeightList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.bindViewModel()
        }
        initWeightMenu()

        viewModel.person.observe(viewLifecycleOwner) {
            //Timber.d("PERSON ${it.toString()}")
            if (it != null) //first launch if db is empty
                bindViewModel(it)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
            updateView(it)
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "No connection", Toast.LENGTH_LONG).show()
        }

        binding.buttonLogout.setOnClickListener {
            LogoutDialog()
                .show(childFragmentManager, "Duration")
        }

        binding.buttonShare.setOnClickListener {
            findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToShareListFragment())
        }

    }

    private fun bindViewModel(person: Person) {

        binding.textName.text = "${person.firstName} ${person.lastName}"
        binding.textMail.text = FormatData.formatDate(person.lastUpdate)
        binding.textFollowingCount.text = person.followingCount.toString()
        binding.textFollowersCount.text = person.followersCount.toString()
        binding.textGender.text = when (binding.textGender.text) {
            "F" -> "Female"
            else -> "Mail"
        }
        binding.textCountry.text = person.country
        binding.spinnerWeight.setSelection(person.weight.toInt() - 10)

        Glide.with(binding.imageAvatar)
            .load(person.avatarUrl)
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_account)
            .into(binding.imageAvatar)
    }

    private fun initWeightMenu() {
        val items = menuList
        val adapter = ArrayAdapter(requireContext(), R.layout.item_exposed_menu, items)
        binding.spinnerWeight.adapter = adapter
        binding.spinnerWeight.onItemSelectedListener = this

    }

    private fun updateView(state: ScreenState) {

        when (state) {
            is ScreenState.DefaultState -> {
                defaultView(true)
            }
            is ScreenState.LoadingState -> {
                defaultView(false)
            }
            is ScreenState.SuccessState -> {

            }
            is ScreenState.ErrorState -> {
                defaultView(true)
            }
            is ScreenState.LogoutState -> {
                Timber.d("LOGOUT STATE ${parentFragment?.findNavController().toString()}")
                parentFragment?.findNavController()
                    ?.navigate(ContainerFragmentDirections.actionContainerFragmentToLoginFragment())
            }
            else -> {}
        }
    }

    private fun defaultView(visibility: Boolean) {
        binding.containerMainView.isVisible = visibility
        binding.progressBar.isVisible = !visibility
    }

    private fun generateWeightList(): List<String> {
        val list = mutableListOf<String>()
        (10..350).forEach { list.add("$it kg") }
        return list
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Timber.d(pos.toString())  //changeWeight in Room Person
        viewModel.updateWeight(pos + 10.0)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun logout() {
        viewModel.logout()
    }


}