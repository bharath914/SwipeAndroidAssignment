package com.bharath.swipeandroidassignment.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bharath.swipeandroidassignment.R
import com.bumptech.glide.Glide

class ImageListAdapter : ListAdapter<Uri, ImageListAdapter.MyViewHolder>(MyDiffUtil()) {
    inner class MyViewHolder(view: View) : ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.file_image)
        fun bind(uri: Uri) {
            Glide.with(itemView)
                .load(uri)
                .override(250, 250)
                .centerCrop()
                .into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.images_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            bind(currentList[position])
        }
    }
}


private class MyDiffUtil : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem.path == newItem.path
    }
}