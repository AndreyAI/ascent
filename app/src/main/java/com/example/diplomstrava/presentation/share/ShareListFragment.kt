package com.example.diplomstrava.presentation.share

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.data.Contact
import com.example.diplomstrava.databinding.FragmentShareListBinding
import com.example.diplomstrava.presentation.ScreenState
import com.example.diplomstrava.presentation.share.adapter.ShareListAdapter
import com.example.diplomstrava.utils.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import timber.log.Timber

@AndroidEntryPoint
class ShareListFragment : Fragment(R.layout.fragment_share_list) {

    private val binding by viewBinding(FragmentShareListBinding::bind)
    private var contactAdapter: ShareListAdapter by autoCleared()
    private val viewModel: ShareListViewModel by viewModels()
    private var errorSnackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getContact()

        viewModel.contacts.observe(viewLifecycleOwner) {
            refreshList(it)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
            updateView(it)
        }

        viewModel.stateSnack.observe(viewLifecycleOwner) {
            if (errorSnackbar?.isShown == false)
                errorSnackbar?.show()
        }

        binding.buttonAllowPermission.setOnClickListener {
            permissionRequest()
        }

        initList()
        initErrorSnack()

    }

    private fun refreshList(contacts: List<Contact>) {
        val animationController: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.layout_animation_recycler
            )
        binding.contactList.layoutAnimation = animationController
        binding.contactList.layoutAnimation.start()
        contactAdapter.items = contacts
    }

    private fun permissionRequest() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.getContact() }
            )
                .launch()
        }
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionDenied() {
        Timber.d("permission denied")
    }

    private fun onContactPermissionNeverAskAgain() {
        Timber.d("ask again")
    }

    private fun initList() {

        contactAdapter = ShareListAdapter {
            composeSmsMessage(it)
        }
        with(binding.contactList) {
            setHasFixedSize(true)
            contactAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    layoutManager?.scrollToPosition(0)
                }
            })
            adapter = contactAdapter
        }
    }

    private fun initErrorSnack() {

        errorSnackbar = Snackbar.make(
            binding.containerProgress,
            R.string.error_permission,
            Snackbar.LENGTH_SHORT
        )

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
            R.drawable.ic_warning,
            0,
            0,
            0
        )
        textView.compoundDrawablePadding = 30
        textView.textSize = View.resolveSize(20, View.MEASURED_SIZE_MASK).toFloat()

        val params = errorSnackbar!!.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin = errorSnackbar!!.view.height

        errorSnackbar!!.view.layoutParams = params

        errorSnackbar!!
            .setAction(" ") {
                errorSnackbar!!.dismiss()
            }

    }

    private fun updateView(state: ScreenState) {

        when (state) {
            is ScreenState.DefaultState -> {
                refreshView(true, false, false, false, false)
            }
            is ScreenState.LoadingState -> {
                refreshView(false, false, false, false, true)
            }
            is ScreenState.SuccessState -> {

            }
            is ScreenState.ErrorState -> {
                refreshView(false, true, true, false, false)
            }
            is ScreenState.EmptyListState -> {
                refreshView(false, false, false, true, false)
            }
            else -> {}
        }
    }

    private fun refreshView(
        contactList: Boolean,
        containerButton: Boolean,
        textAllow: Boolean,
        textEmptyList: Boolean,
        progressBar: Boolean
    ) {
        binding.contactList.isVisible = contactList
        binding.containerButton.isVisible = containerButton
        binding.textAllow.isVisible = textAllow
        binding.textEmptyList.isVisible = textEmptyList
        binding.progressBar.isVisible = progressBar
    }

    private fun composeSmsMessage(contact: Contact) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            data = Uri.parse("smsto:${contact.phones}")
            putExtra("sms_body", getString(R.string.url_strava_profile))
        }
        startActivity(intent)
        Timber.d("start activity")
    }
}