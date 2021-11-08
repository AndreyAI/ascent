package com.example.diplomstrava.presentation.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.databinding.ItemActivityBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ActivityAdapterDelegate(
    private val userName: String,
    private val avatarUrl: String,
    private val onShareActivity: (Activity) -> Unit
) :
    AbsListItemAdapterDelegate<Activity, Activity, ActivityAdapterDelegate.Holder>() {


    override fun isForViewType(
        item: Activity,
        items: MutableList<Activity>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding, userName, avatarUrl, onShareActivity)
    }

    override fun onBindViewHolder(item: Activity, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemActivityBinding,
        private val userName: String,
        private val avatarUrl: String,
        private val onShareActivity: (Activity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentActivity: Activity? = null

        init {
            binding.imageShare.setOnClickListener {
                currentActivity?.let {
                    onShareActivity
                }
            }
        }

        fun bind(activity: Activity) {
            currentActivity = activity
            binding.root.setOnClickListener {
                currentActivity?.let {
                    onShareActivity(it)
                }
            }
//            Glide.with(itemView)
//                .load(this.avatarUrl)
//                .into(binding.imageAvatar)
            binding.textName.text = userName
            binding.textDate.text = activity.date
            binding.textTitleActivity.text = activity.name
            binding.textDistanceField.text = formatKm(activity.distance)
            binding.textTimeField.text = activity.time.toString()
            binding.textElevationField.text = formatM(activity.elevation) // or foot?
            binding.textActivityGroup.text = activity.type
            if (activity.description == null) binding.textDescription.isVisible = false
            else binding.textDescription.text = activity.description

        }

    }

    companion object {

        fun formatKm(meters: Double): String {
            return "${meters / 1000.0} km"
        }

        fun formatM(meters: Long): String {
            return "$meters m"
        }


    }

}

/*
package com.example.materialdesigne.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.materialdesigne.AndroidCard
import com.example.materialdesigne.databinding.ItemCardBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class AndroidAdapterDelegate() :
    AbsListItemAdapterDelegate<AndroidCard, AndroidCard, AndroidAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: AndroidCard,
        items: MutableList<AndroidCard>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(item: AndroidCard, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentCard: AndroidCard? = null

//        init {
//            binding.root.setOnClickListener {  }
//        }

        fun bind(card: AndroidCard) {
            currentCard = card
            binding.textTitle.text = card.title
            binding.textPrice.text = card.price
            Glide.with(itemView)
                .load(card.image)
                .into(binding.imageUrl)
        }

    }
}
 */