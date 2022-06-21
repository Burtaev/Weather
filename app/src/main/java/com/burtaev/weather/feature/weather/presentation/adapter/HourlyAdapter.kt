package com.burtaev.weather.feature.weather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burtaev.weather.databinding.FragmentHoursItemBinding
import com.burtaev.weather.feature.weather.domain.models.Hour

class HourlyAdapter :
    ListAdapter<Hour, HourlyViewHolder>(ContactDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentHoursItemBinding.inflate(layoutInflater, parent, false)
        return HourlyViewHolder(
            binding = binding
        )
    }

    override fun onBindViewHolder(
        holder: HourlyViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}

class HourlyViewHolder(
    private val binding: FragmentHoursItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Hour) {
        with(binding) {
            tvTime.text = item.time
            tvTemp.text = item.temp
            Glide.with(root.context)
                .load(item.condition.iconUrl)
                .into(weatherIco)
        }
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<Hour>() {

    override fun areItemsTheSame(oldItem: Hour, newItem: Hour) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Hour, newItem: Hour) =
        oldItem == newItem
}
