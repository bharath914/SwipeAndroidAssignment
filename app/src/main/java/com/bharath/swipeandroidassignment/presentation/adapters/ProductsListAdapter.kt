package com.bharath.swipeandroidassignment.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.helpers.Validators
import com.bumptech.glide.Glide

class ProductsListAdapter(
    val listener: OnClickListener,
) :
    ListAdapter<ProductEntity, ProductsListAdapter.MyViewHolder>(HomeListCallBack()) {
    inner class MyViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {

        private val cardView: CardView = itemView.findViewById(R.id.ListCard)
        private val imageView: ImageView = itemView.findViewById(R.id.item_image)
        private val name: TextView = itemView.findViewById(R.id.item_name)
        private val type: TextView = itemView.findViewById(R.id.item_type)
        private val price: TextView = itemView.findViewById(R.id.item_price)


        override fun onClick(p0: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.onClick(pos)
            }
        }

        init {
            cardView.setOnClickListener {
                listener.onClick(index = adapterPosition)
            }
        }

        fun bind(entity: ProductEntity) {
            val imageUrl = entity.image
            Glide.with(itemView.context).load(
                if (entity.image.isNotBlank() && Validators.isImageUrlValid(entity.image)) {
                    imageUrl
                } else {
                    R.drawable.picture
                }
            ).error(R.drawable.picture)
                .override(200, 200)
                .into(imageView)

            name.text = entity.productName.trim()
            type.text = entity.productType.trim()
            price.text = "${entity.price}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    fun submitNewList(list: List<ProductEntity>) {
        submitList(list)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val entity = currentList[position]
        holder.apply {
            bind(entity)

        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}

class HomeListCallBack : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem == newItem
    }
}