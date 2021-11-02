package com.example.diplomstrava.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentContainerBinding

class ContainerFragment: Fragment(R.layout.fragment_container) {

    private val binding by viewBinding(FragmentContainerBinding::bind)
    private var state = HOME

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInit()
        listenersInit()
    }

    private fun listenersInit() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    replaceFragment(PersonFragment())
                    state = HOME
                    true
                }
                R.id.navList -> {
                    replaceFragment(ActivitiesFragment())
                    state = LIST
                    true
                }
                else -> false
            }
        }
    }

    private fun fragmentInit() {
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

/*


class MainFragment : Fragment(R.layout.fragment_main) {

    private var state = ANDROID
    val binding by viewBinding(FragmentMainBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInit()
        listenersInit()
    }

    private fun listenersInit() {

        binding. bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_android -> {
                    replaceFragment(AndroidFragment())
                    state = ANDROID
                    true
                }
                R.id.nav_ios -> {
                    replaceFragment(EmptyFragment.newInstance("Fragment № 2"))
                    state = IOS
                    true
                }
                R.id.nav_desktop -> {
                    replaceFragment(EmptyFragment.newInstance("Fragment № 3"))
                    state = DESKTOP
                    true
                }
                else -> false
            }
        }
    }

    private fun fragmentInit() {
        when (state) {
            ANDROID -> replaceFragment(AndroidFragment())
            IOS -> replaceFragment(EmptyFragment())
            DESKTOP -> replaceFragment(EmptyFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    companion object {
        private const val ANDROID = "Android"
        private const val IOS = "Ios"
        private const val DESKTOP = "Desktop"
    }

}
 */