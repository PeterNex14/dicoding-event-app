package com.gabsee.dicodingevents.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.databinding.ItemEventHorizontalBinding

class HorizontalEventAdapter(
    private val onItemClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, HorizontalEventAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemEventHorizontalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class ViewHolder (
        private val binding: ItemEventHorizontalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(event.mediaCover)
                    .into(ivEventLogo)
                tvEventName.text = event.title
                tvOwnerName.text = event.organizer

                root.setOnClickListener { onItemClick(event) }
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<EventEntity>() {
        override fun areItemsTheSame(
            oldItem: EventEntity,
            newItem: EventEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EventEntity,
            newItem: EventEntity
        ): Boolean {
            return oldItem == newItem
        }
    }



}