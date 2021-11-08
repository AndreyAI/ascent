package com.example.diplomstrava.presentation.activities.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.diplomstrava.data.Activity
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ActivityListAdapter(
    userName: String,
    avatarUrl: String,
    onShareActivity: (Activity) -> Unit
) : AsyncListDifferDelegationAdapter<Activity>(ActivityDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ActivityAdapterDelegate(userName, avatarUrl, onShareActivity))
    }

    class ActivityDiffUtilCallback : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }
    }

}