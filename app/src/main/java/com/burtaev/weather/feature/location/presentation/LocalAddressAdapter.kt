package com.burtaev.weather.feature.location.presentation

import android.location.Address
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.burtaev.weather.databinding.FragmentLocationItemBinding

class LocalAddressAdapter(private val listener: (Address) -> Unit) :
    ListAdapter<Address, AddressViewHolder>(AddressDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentLocationItemBinding.inflate(layoutInflater, parent, false)
        return AddressViewHolder(
            binding = binding,
            listener = { position -> listener.invoke(getItem(position)) }
        )
    }

    override fun onBindViewHolder(
        holder: AddressViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}

class AddressViewHolder(
    private val binding: FragmentLocationItemBinding,
    listener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener(position)
            }
        }
    }

    fun bind(item: Address) {
        with(binding) {
            tvAddress.text = item.getAddressLine(0)
        }
    }
}

class AddressDiffCallback : DiffUtil.ItemCallback<Address>() {

    override fun areItemsTheSame(oldItem: Address, newItem: Address) =
        oldItem.getAddressLine(0) == newItem.getAddressLine(0)

    override fun areContentsTheSame(oldItem: Address, newItem: Address) =
        oldItem.hashCode() == newItem.hashCode()
}
