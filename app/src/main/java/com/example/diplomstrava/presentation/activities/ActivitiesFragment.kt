package com.example.diplomstrava.presentation.activities

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
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
import android.view.animation.LayoutAnimationController
import com.example.diplomstrava.data.PersonWithActivity


@AndroidEntryPoint
class ActivitiesFragment : Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)
    private var activityAdapter: ActivityListAdapter by autoCleared()
    private val viewModel: ActivitiesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            viewModel.bindViewModel()

        viewModel.activities.observe(viewLifecycleOwner) {
            if (it != null) {
                refreshList(it)
            }
        }

        initList()
        listenersInit()
    }

    private fun refreshList(personWithActivities: List<PersonWithActivity>) {
        val animationController: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.layout_animation_recycler
            )

        binding.listActivities.layoutAnimation = animationController
        binding.listActivities.layoutAnimation.start()
        activityAdapter.items = personWithActivities
        activityAdapter.notifyDataSetChanged()
        Timber.d(personWithActivities.toString())
    }


    private fun listenersInit() {
        binding.fabAdd.setOnClickListener {
            addActivityNav()
        }

        binding.swipeRefresh.setOnRefreshListener {
            Timber.d("swipe refresh")
            viewModel.bindViewModel()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refresh -> {
                    //viewModel.bindViewModel()

                    true
                }
                else -> false
            }
        }
    }

    private fun initList() {
        activityAdapter = ActivityListAdapter() {

        }
        with(binding.listActivities) {
            adapter = activityAdapter
            setHasFixedSize(true)
//            val animationController: LayoutAnimationController =
//                AnimationUtils.loadLayoutAnimation(
//                    requireContext(),
//                    R.anim.layout_animation_recycler
//                )
//            layoutAnimation = animationController

        }
    }


    private fun shareActivity(activity: Activity) {
        Timber.d(activity.description)
    }

    private fun addActivityNav() {
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}