package com.example.diplomstrava.presentation.share.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.diplomstrava.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ShareListAdapter(
    onShare: (Contact) -> Unit
) : AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ShareListAdapterDelegate(onShare))
    }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {


        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean {
            return oldItem == newItem
        }
    }

}

/*
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
 */