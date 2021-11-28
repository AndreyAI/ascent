package com.example.diplomstrava.presentation.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.diplomstrava.R
import com.example.diplomstrava.data.PersonWithActivity
import com.example.diplomstrava.databinding.ItemActivityBinding
import com.example.diplomstrava.utils.FormatData.formatDate
import com.example.diplomstrava.utils.FormatData.formatKm
import com.example.diplomstrava.utils.FormatData.formatM
import com.example.diplomstrava.utils.FormatData.formatTime
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate


class ActivityAdapterDelegate(
    private val onShareActivity: (PersonWithActivity) -> Unit
) :
    AbsListItemAdapterDelegate<PersonWithActivity, PersonWithActivity, ActivityAdapterDelegate.Holder>() {


    override fun isForViewType(
        item: PersonWithActivity,
        items: MutableList<PersonWithActivity>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, onShareActivity)
    }

    override fun onBindViewHolder(
        item: PersonWithActivity,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemActivityBinding,
        private val onShareActivity: (PersonWithActivity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentActivity: PersonWithActivity? = null

        init {
            binding.imageShare.setOnClickListener {
                currentActivity?.let {
                    onShareActivity(it)
                }
            }
        }

        fun bind(activity: PersonWithActivity) {
            currentActivity = activity

            Glide.with(itemView)
                .load(activity.avatarUrl)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_account)
                .into(binding.imageAvatar)
            binding.textName.text = "${activity.firstName} ${activity.lastName}"
            binding.textDate.text = formatDate(activity.activity.date)
            binding.textTitleActivity.text = activity.activity.name
            binding.textDistanceField.text = formatKm(activity.activity.distance)
            binding.textTimeField.text = formatTime(activity.activity.time)
            binding.textElevationField.text = formatM(activity.activity.elevation) // or foot?
            binding.textActivityGroup.text = activity.activity.type
            if (activity.activity.description.isNullOrEmpty()) {
                binding.textDescription.isVisible =
                    false
            } else {
                binding.textDescription.isVisible = true
                binding.textDescription.text = activity.activity.description
            }

        }

    }
}