package com.burtaev.weather.feature.weather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burtaev.weather.databinding.FragmentDailyItemBinding
import com.burtaev.weather.feature.weather.domain.models.Forecastday

class DailyAdapter :
    ListAdapter<Forecastday, DailyViewHolder>(DailyDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentDailyItemBinding.inflate(layoutInflater, parent, false)
        return DailyViewHolder(
            binding = binding
        )
    }

    override fun onBindViewHolder(
        holder: DailyViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}

class DailyViewHolder(
    private val binding: FragmentDailyItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Forecastday) {
        with(binding) {
            tvDayOfWeek.text = item.date
            tvMaxTemp.text = item.day.maxTemp
            tvMinTemp.text = item.day.minTemp
            tvDescriptionDay.text = item.day.condition.description
            Glide.with(root.context)
                .load(item.day.condition.iconUrl)
                .into(ivIconDay)

            val adapterHours = HourlyAdapter()
            rvHours.adapter = adapterHours
            adapterHours.submitList(item.hours)
        }
    }
}

class DailyDiffCallback : DiffUtil.ItemCallback<Forecastday>() {

    override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday) =
        oldItem == newItem
}
