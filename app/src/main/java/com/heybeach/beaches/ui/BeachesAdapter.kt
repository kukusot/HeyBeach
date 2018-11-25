package com.heybeach.beaches.ui

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.R
import com.heybeach.beaches.domain.model.Beach
import com.heybeach.images.ImageLoader
import com.heybeach.utils.getLayoutInflater
import kotlinx.android.synthetic.main.item_beach.view.*

class BeachesAdapter : PagedListAdapter<Beach, BeachesAdapter.BeachViewHolder>(BeachesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeachViewHolder {
        val itemView = parent.context.getLayoutInflater().inflate(R.layout.item_beach, parent, false)
        return BeachViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeachViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BeachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(image: Beach?) {
            itemView.apply {
                ImageLoader.loadImage(image?.url, imageView)
                imageView.ratio = image?.ratio() ?: 0f
                imageName.text = image?.name ?: ""
            }
        }
    }
}

object BeachesDiffCallback : DiffUtil.ItemCallback<Beach>() {

    override fun areItemsTheSame(oldItem: Beach, newItem: Beach) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Beach, newItem: Beach) = oldItem == newItem
}