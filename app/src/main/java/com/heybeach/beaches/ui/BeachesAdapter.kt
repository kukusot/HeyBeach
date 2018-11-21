package com.heybeach.beaches.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.R
import com.heybeach.beaches.domain.model.Beach
import com.heybeach.images.ImageLoader
import com.heybeach.utils.getLayoutInflater
import kotlinx.android.synthetic.main.item_beach.view.*

class BeachesAdapter : RecyclerView.Adapter<BeachesAdapter.BeachViewHolder>() {

    var beaches: List<Beach> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeachViewHolder {
        val itemView = parent.context.getLayoutInflater().inflate(R.layout.item_beach, parent, false)
        return BeachViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return beaches.size
    }

    override fun onBindViewHolder(holder: BeachViewHolder, position: Int) {
        holder.onBind(beaches[position])
    }

    class BeachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(image: Beach?) {
            ImageLoader.loadImage(image!!.url, itemView.imageView)
        }
    }
}