package com.example.diplomstrava.presentation.activities.adapter

import android.annotation.SuppressLint
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

        @SuppressLint("SetTextI18n")
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
            setImageActivity(activity.activity.type)
        }

        private fun setImageActivity(type: String){
            when (type) {

                "AlpineSki" -> uploadImage(R.drawable.alpineski)
                "BackcountrySki" -> uploadImage(R.drawable.backcountry)
                "Canoeing" -> uploadImage(R.drawable.canoeing)
                "Crossfit" -> uploadImage(R.drawable.crossfit)
                "EBikeRide" -> uploadImage(R.drawable.ebikeride)
                "Elliptical" -> uploadImage(R.drawable.elliptical)
                "Golf" -> uploadImage(R.drawable.golf)
                "Handcycle" -> uploadImage(R.drawable.handcycle)
                "Hike" -> uploadImage(R.drawable.hike)
                "IceSkate" -> uploadImage(R.drawable.iceskate)
                "InlineSkate" -> uploadImage(R.drawable.inlineskate)
                "Kayaking" -> uploadImage(R.drawable.kayaking)
                "Kitesurf" -> uploadImage(R.drawable.kitesurf)
                "NordicSki" -> uploadImage(R.drawable.nordicski)
                "Ride" -> uploadImage(R.drawable.ride)
                "RockClimbing" -> uploadImage(R.drawable.rockclimbing)
                "RollerSki" -> uploadImage(R.drawable.rollerski)
                "Rowing" -> uploadImage(R.drawable.rowing)
                "Run" -> uploadImage(R.drawable.activity_run)
                "Sail" -> uploadImage(R.drawable.sail)
                "Skateboard" -> uploadImage(R.drawable.skateboard)
                "Snowboard" -> uploadImage(R.drawable.snowboard)
                "Snowshoe" -> uploadImage(R.drawable.snowshoe)
                "Soccer" -> uploadImage(R.drawable.soccer)
                "StairStepper" -> uploadImage(R.drawable.stairstepper)
                "StandUpPaddling" -> uploadImage(R.drawable.stand_up_paddlong)
                "Surfing" -> uploadImage(R.drawable.surfing)
                "Swim" -> uploadImage(R.drawable.swim)
                "Velomobile" -> uploadImage(R.drawable.velomobile)
                "VirtualRide" -> uploadImage(R.drawable.virtualride)
                "VirtualRun" -> uploadImage(R.drawable.virtualrun)
                "Walk" -> uploadImage(R.drawable.walk)
                "WeightTraining" -> uploadImage(R.drawable.weight_training)
                //"Wheelchair" -> uploadImage(R.drawable.)
                "Workout" -> uploadImage(R.drawable.workout)
                "Yoga" -> uploadImage(R.drawable.yoga)
            }
        }

        private fun uploadImage(res: Int){
            Glide.with(itemView)
                .load(res)
                .into(binding.imageActivity)
        }

    }
}