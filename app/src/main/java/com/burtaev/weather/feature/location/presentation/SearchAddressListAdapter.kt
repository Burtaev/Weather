package com.burtaev.weather.feature.location.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.burtaev.weather.databinding.FragmentLocationItemBinding
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity

class SearchAddressListAdapter(
    private val listenerSelect: (AddressEntity) -> Unit,
    private val listenerDelete: (AddressEntity) -> Unit
) :
    ListAdapter<AddressEntity, AddressListViewHolder>(AddressListDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentLocationItemBinding.inflate(layoutInflater, parent, false)
        return AddressListViewHolder(
            binding = binding,
            listenerSelect = { position -> listenerSelect.invoke(getItem(position)) },
            listenerDelete = { position -> listenerDelete.invoke(getItem(position)) }
        )
    }

    override fun onBindViewHolder(
        holder: AddressListViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}

class AddressListViewHolder(
    private val binding: FragmentLocationItemBinding,
    listenerSelect: (Int) -> Unit,
    listenerDelete: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {

        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listenerSelect(position)
            }
        }
        binding.deleteItem.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listenerDelete(position)
            }
        }
    }

    fun bind(item: AddressEntity) {
        with(binding) {
            tvAddress.text = item.address
            deleteItem.visibility = View.VISIBLE
        }
    }
}

class AddressListDiffCallback : DiffUtil.ItemCallback<AddressEntity>() {

    override fun areItemsTheSame(oldItem: AddressEntity, newItem: AddressEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AddressEntity, newItem: AddressEntity) =
        oldItem == newItem
}
