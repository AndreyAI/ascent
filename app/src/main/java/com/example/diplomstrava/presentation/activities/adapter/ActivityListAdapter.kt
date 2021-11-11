package com.example.diplomstrava.presentation.activities.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.diplomstrava.data.PersonWithActivity
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ActivityListAdapter(
    onShareActivity: (PersonWithActivity) -> Unit
) : AsyncListDifferDelegationAdapter<PersonWithActivity>(ActivityDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ActivityAdapterDelegate(onShareActivity))
    }

    class ActivityDiffUtilCallback : DiffUtil.ItemCallback<PersonWithActivity>() {
        override fun areItemsTheSame(oldItem: PersonWithActivity, newItem: PersonWithActivity): Boolean {
            return oldItem.activity.id == newItem.activity.id
        }

        override fun areContentsTheSame(oldItem: PersonWithActivity, newItem: PersonWithActivity): Boolean {
            return oldItem == newItem
        }
    }

}