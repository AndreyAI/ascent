package com.example.diplomstrava.presentation.containerfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentContainerBinding
import com.example.diplomstrava.presentation.person.PersonFragment
import com.example.diplomstrava.presentation.activities.ActivitiesFragment
import timber.log.Timber

class ContainerFragment : Fragment(R.layout.fragment_container) {

    private val binding by viewBinding(FragmentContainerBinding::bind)
    private val viewModel: ContainerFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            fragmentInit(HOME)
            Timber.d("Init")
        }

        viewModel.currentPage.observe(viewLifecycleOwner) {
            fragmentInit(it)
        }
        listenersInit()
    }

    private fun listenersInit() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    fragmentInit(HOME)
                    viewModel.saveCurrentPos(HOME)
                    true
                }
                R.id.navList -> {
                    fragmentInit(LIST)
                    viewModel.saveCurrentPos(LIST)
                    true
                }
                else -> false
            }
        }
    }

    private fun fragmentInit(state: String) {
        when (state) {
            HOME -> if (childFragmentManager.findFragmentByTag(HOME) !is PersonFragment) {
                replaceFragment(
                    PersonFragment(), HOME
                )
                Timber.d("replace home")
            }
            LIST -> if (childFragmentManager.findFragmentByTag(LIST) !is ActivitiesFragment) {
                replaceFragment(
                    ActivitiesFragment(), LIST
                )
                Timber.d("replace list")
            }
        }
    }


    private fun replaceFragment(fragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    companion object {
        private const val HOME = "Home"
        private const val LIST = "List"
    }
}
