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
                    viewModel.saveCurrentPos(HOME)
                    true
                }
                R.id.navList -> {
                    viewModel.saveCurrentPos(LIST)
                    true
                }
                else -> false
            }
        }
    }

    private fun fragmentInit(state: String) {
        when (state) {
            HOME -> //if (childFragmentManager.findFragmentByTag(HOME) !is PersonFragment) {
            {
//                if (childFragmentManager.findFragmentByTag(HOME) != null) {
//                    childFragmentManager.popBackStack(HOME, 0)
//                    Timber.d(" pop home")
//                } else
                displayFragment(
                    PersonFragment(), HOME, LIST
                )
                Timber.d("replace home")
                // }
            }
            LIST -> //if (childFragmentManager.findFragmentByTag(LIST) !is ActivitiesFragment) {
            {
//                if (childFragmentManager.findFragmentByTag(LIST) != null) {
//                    childFragmentManager.popBackStack(LIST, 0)
//                    Timber.d(" pop list")
//                } else
                displayFragment(
                    ActivitiesFragment(), LIST, HOME
                )
                Timber.d("replace list")
                // }
            }
        }
    }


    private fun displayFragment(fragment: Fragment, tagShow: String?, tagHide: String?) {
        val ft = childFragmentManager.beginTransaction()
        if (childFragmentManager.findFragmentByTag(tagShow)?.isAdded == true)
            ft.show(childFragmentManager.findFragmentByTag(tagShow)!!)
        else
            ft.add(R.id.fragmentContainer, fragment, tagShow)
        if (childFragmentManager.findFragmentByTag(tagHide)?.isAdded == true)
            ft.hide(childFragmentManager.findFragmentByTag(tagHide)!!)
        ft.commit()
    }

    companion object {
        private const val HOME = "Home"
        private const val LIST = "List"
    }

}
