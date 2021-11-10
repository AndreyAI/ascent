package com.example.diplomstrava.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.databinding.FragmentActivitiesBinding
import com.example.diplomstrava.presentation.activities.adapter.ActivityListAdapter
import com.example.diplomstrava.presentation.containerfragment.ContainerFragmentDirections
import com.example.diplomstrava.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ActivitiesFragment : Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)
    private var activityAdapter: ActivityListAdapter by autoCleared()
    private val viewModel: ActivitiesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        listenersInit()
        bindViewModel()

    }

    private fun bindViewModel() {
        viewModel.activities.observe(viewLifecycleOwner) {
            activityAdapter.items = it
            Timber.d(it.toString())
        }
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

    private fun initList() {
        activityAdapter = ActivityListAdapter(
            "andrey alyabev", "sdf"
        ) {
            shareActivity(it)
        }
        with(binding.listActivities) {
            adapter = activityAdapter
            setHasFixedSize(true)
        }
    }


    private fun shareActivity(activity: Activity) {
        Timber.d(activity.description)
    }

    private fun addActivityNav() {
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}