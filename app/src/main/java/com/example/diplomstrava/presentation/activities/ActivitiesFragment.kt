package com.example.diplomstrava.presentation.activities

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.presentation.activities.adapter.ActivityListAdapter
import com.example.diplomstrava.presentation.containerfragment.ContainerFragmentDirections
import com.example.diplomstrava.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import android.view.animation.LayoutAnimationController
import com.example.diplomstrava.data.PersonWithActivity

import com.example.diplomstrava.presentation.ScreenState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import android.view.Gravity
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentActivitiesBinding


@AndroidEntryPoint
class ActivitiesFragment : Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)
    private var activityAdapter: ActivityListAdapter by autoCleared()
    private val viewModel: ActivitiesViewModel by viewModels()

    private var errorSnackbar: Snackbar? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            viewModel.bindViewModel(false)

        viewModel.activities.observe(viewLifecycleOwner) {
            if (it != null) {
                refreshList(it)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            updateView(it)
        }

        viewModel.stateSnack.observe(viewLifecycleOwner) {
            if (errorSnackbar?.isShown == false)
                errorSnackbar?.show()
        }

        initErrorSnack()
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
            viewModel.bindViewModel(true)
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
        activityAdapter = ActivityListAdapter {
            Timber.d("CLICK")
        }
        with(binding.listActivities) {
            //adapter = activityAdapter
            setHasFixedSize(true)
            activityAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    layoutManager?.scrollToPosition(0)
                }
            })
            adapter = activityAdapter
        }
    }

    private fun updateView(state: ScreenState) {

        when (state) {
            is ScreenState.DefaultState -> {
                refreshView(false, true, false, false)
            }
            is ScreenState.LoadingState -> {
                refreshView(true, false, false, false)
            }
            is ScreenState.SuccessState -> {

            }
            is ScreenState.SwipeRefresh -> {
                refreshView(false, true, true, false)
                Timber.d("Swipe")
            }
            is ScreenState.ErrorState -> {
                refreshView(false, true, true, false)
                Timber.d("error state")
            }
            is ScreenState.EmptyListState -> {
                refreshView(false, true, false, true)
            }
            else -> {}
        }
    }

    private fun refreshView(
        progressLoading: Boolean,
        swipeContainer: Boolean,
        progressSwipe: Boolean,
        emptyList: Boolean
    ) {
        binding.progressBar.isVisible = progressLoading
        binding.swipeRefresh.isVisible = swipeContainer
        binding.containerSnack.isVisible = progressSwipe
        binding.textEmptyList.isVisible = emptyList
    }

    private fun initErrorSnack() {

        errorSnackbar = Snackbar.make(
            binding.containerSnack,
            R.string.loaded_from_cash,
            Snackbar.LENGTH_SHORT
        )
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    //refreshView(false, true, false)
                    binding.progressBarSwipe.isVisible = true
                    viewModel.bindDefaultModel()
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                    binding.progressBarSwipe.isVisible = false
                }
            })

        val textViewButton =
            errorSnackbar!!.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textViewButton.text = " " // clear the text to keep only the icon
        textViewButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_close,
            0
        )

        val textView =
            errorSnackbar!!.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_alarm,
            0,
            0,
            0
        )


        val params = errorSnackbar!!.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin = errorSnackbar!!.view.height

        errorSnackbar!!.view.layoutParams = params

        errorSnackbar!!
            .setAction(" ") {
                errorSnackbar!!.dismiss()
            }

    }


    private fun shareActivity(activity: Activity) {
        Timber.d(activity.description)
    }

    private fun addActivityNav() {
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}