package com.example.diplomstrava.presentation.activities


import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
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
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomstrava.R
import android.text.method.LinkMovementMethod
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.SpannableString
import com.example.diplomstrava.databinding.FragmentActivitiesBinding


@AndroidEntryPoint
class ActivitiesFragment : Fragment(R.layout.fragment_activities) {

    private val binding by viewBinding(FragmentActivitiesBinding::bind)
    private var activityAdapter: ActivityListAdapter by autoCleared()
    private val viewModel: ActivitiesViewModel by viewModels()

    private var errorSnackbar: Snackbar? = null
    private var retrySnackbar: Snackbar? = null


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
            Timber.d(it.toString())
            updateView(it)
        }

        viewModel.stateSnack.observe(viewLifecycleOwner) {
            if (errorSnackbar?.isShown == false)
                errorSnackbar?.show()

        }

        viewModel.retrySnack.observe(viewLifecycleOwner) {
            if (retrySnackbar?.isShown == false)
                retrySnackbar?.show()
        }


        initRetrySnack()
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
    }


    private fun listenersInit() {
        binding.fabAdd.setOnClickListener {
            addActivityNav()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.bindViewModel(true)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.refresh -> {
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
                refreshView(false, true, false, false, false, false)
            }
            is ScreenState.LoadingState -> {
                refreshView(true, false, false, false, false, false)
            }
            is ScreenState.SuccessState -> {

            }
            is ScreenState.SwipeRefresh -> {
                refreshView(false, true, true, false, false, false)
            }
            is ScreenState.ErrorState -> {
                refreshView(false, true, false, false, false, true)
            }
            is ScreenState.EmptyListState -> {
                refreshView(false, true, false, true, false, false)
            }
            is ScreenState.RetryListState -> {
                refreshView(false, true, false, false, true, false)
            }
            else -> {}
        }
    }

    private fun refreshView(
        progressLoading: Boolean,
        swipeContainer: Boolean,
        progressContainer: Boolean,
        emptyList: Boolean,
        retry: Boolean,
        error: Boolean
    ) {
        binding.progressBar.isVisible = progressLoading
        binding.containerProgress.isVisible = progressContainer
        binding.textEmptyList.isVisible = emptyList
        binding.containerSnackRetry.isVisible = retry
        binding.containerSnackError.isVisible = error
        binding.swipeRefresh.isVisible = swipeContainer
    }

    private fun initErrorSnack() {

        errorSnackbar = Snackbar.make(
            binding.containerSnackError,
            R.string.loaded_from_cash,
            Snackbar.LENGTH_LONG
        )
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    viewModel.bindDefaultModel()
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)

                }
            })

        val textViewButton =
            errorSnackbar!!.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textViewButton.text = " "
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
        textView.textSize = View.resolveSize(20, View.MEASURED_SIZE_MASK).toFloat()

        errorSnackbar!!
            .setAction(" ") {
                errorSnackbar!!.dismiss()
            }

    }


    private fun initRetrySnack() {

        retrySnackbar = Snackbar.make(
            binding.containerSnackRetry,
            R.string.retry_snackbar_text,
            Snackbar.LENGTH_SHORT
        )

        val textViewButton =
            retrySnackbar!!.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textViewButton.text = " " // clear the text to keep only the icon
        textViewButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_close,
            0
        )

        val textView =
            retrySnackbar!!.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_alarm,
            0,
            0,
            0
        )
        textView.compoundDrawablePadding = 50

        val ss = SpannableString(textView.text)

        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.bindViewModel(false)
            }
        }

        val startIndex = textView.text.indexOf("Retry.")
        val endIndex = startIndex + "Retry.".length
        ss.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = ss
        textView.textSize = View.resolveSize(20, View.MEASURED_SIZE_MASK).toFloat()
        textView.movementMethod = LinkMovementMethod.getInstance()

        retrySnackbar!!
            .setAction(" ") {
                retrySnackbar!!.dismiss()
            }
    }

    private fun addActivityNav() {
        findNavController().navigate(ContainerFragmentDirections.actionContainerFragmentToAddActivityFragment())
    }
}