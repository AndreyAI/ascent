package com.example.diplomstrava.presentation.share.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.diplomstrava.R
import com.example.diplomstrava.data.Contact
import com.example.diplomstrava.databinding.ItemShareBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ShareListAdapterDelegate(
    private val onShare: (Contact) -> Unit
) :
    AbsListItemAdapterDelegate<Contact, Contact, ShareListAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Contact,
        items: MutableList<Contact>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemShareBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, onShare)
    }

    override fun onBindViewHolder(
        item: Contact,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemShareBinding,
        private val onShare: (Contact) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentContact: Contact? = null

        init {
            binding.root.setOnClickListener {
                currentContact?.let {
                    onShare(it)
                }
            }
        }

        fun bind(contact: Contact) {
            currentContact = contact

            Glide.with(itemView)
                .load(Uri.parse(contact.avatarUri))
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_contact_error)
                .error(R.drawable.ic_contact_placeholder)
                .into(binding.imageAvatar)


            binding.textName.text = contact.name
            binding.textPhone.text = contact.phones

        }

    }

}
