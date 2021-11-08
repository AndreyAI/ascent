package com.example.diplomstrava.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentActivitiesBinding
import com.example.diplomstrava.presentation.ContainerFragmentDirections
import com.example.diplomstrava.presentation.activities.adapter.ActivityListAdapter
import com.example.diplomstrava.utils.autoCleared
import timber.log.Timber

class ActivitiesFragment : Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)

    //private var activityAdapter: ActivityListAdapter by autoCleared()
    private val viewModel: ActivitiesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenersInit()
        bindViewModel()

    }

    private fun bindViewModel() {

        viewModel.activities.observe(viewLifecycleOwner) { Timber.d(it.toString()) }

//        lifecycleScope.launch {
//
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//                viewModel.movieList.collect {
//                    movieAdapter.items = it
//                    Timber.d(Thread.currentThread().toString())
//                }
//            }
//        }

    }

    private fun listenersInit() {
        binding.fabAdd.setOnClickListener {
            addActivityNav()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refresh -> {
                    viewModel.bindViewModel()
                    true
                }
                else -> false
            }
        }
    }

    private fun addActivityNav() {
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}