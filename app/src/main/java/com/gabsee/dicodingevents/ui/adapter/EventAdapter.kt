package com.gabsee.dicodingevents.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabsee.dicodingevents.R
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.databinding.ItemEventBinding

class EventAdapter(
    private val onBookmarkClick: (EventEntity) -> Unit,
    private val onItemClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class MyViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvEventName.text = event.title
            binding.tvEventOwner.text =
                itemView.context.getString(R.string.event_owner, event.organizer)

            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.ivEventLogo)

            val bookmarkIcon = if (event.isBookmarked) {
                R.drawable.ic_bookmark
            } else {
                R.drawable.ic_bookmark_border
            }

            binding.ivBookmark.setImageResource(bookmarkIcon)
            binding.ivBookmark.setOnClickListener {
                onBookmarkClick(event)
            }
            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
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
}
