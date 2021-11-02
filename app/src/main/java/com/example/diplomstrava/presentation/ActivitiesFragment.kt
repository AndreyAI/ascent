package com.example.diplomstrava.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentActivitiesBinding

class ActivitiesFragment: Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenersInit()

    }

    private fun listenersInit(){
        binding.fabAdd.setOnClickListener {
            addActivityNav()
        }
    }

    private fun addActivityNav(){
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}