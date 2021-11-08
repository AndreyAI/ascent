package com.example.diplomstrava.presentation.containerfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentContainerBinding
import com.example.diplomstrava.presentation.PersonFragment
import com.example.diplomstrava.presentation.activities.ActivitiesFragment

class ContainerFragment: Fragment(R.layout.fragment_container) {

    private val binding by viewBinding(FragmentContainerBinding::bind)
    private val viewModel: ContainerFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null) viewModel.saveCurrentPos(HOME)

        viewModel.currentPage.observe(viewLifecycleOwner) {
            fragmentInit(it)
        }
        listenersInit()
    }

    private fun listenersInit() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    replaceFragment(PersonFragment())
                    viewModel.saveCurrentPos(HOME)
                    //state = HOME
                    true
                }
                R.id.navList -> {
                    replaceFragment(ActivitiesFragment())
                    viewModel.saveCurrentPos(LIST)
                    //state = LIST
                    true
                }
                else -> false
            }
        }
    }

    private fun fragmentInit(state: String) {
        when (state) {
            HOME -> replaceFragment(PersonFragment())
            LIST -> replaceFragment(ActivitiesFragment())
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    companion object {
        private const val HOME = "Home"
        private const val LIST = "List"
    }
}
