package com.bharath.swipeandroidassignment.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.helpers.Validators
import com.bharath.swipeandroidassignment.helpers.toIndianNumberSystem
import com.bumptech.glide.Glide

class ProductsListAdapter(
    private val listener: OnClickListener,
    private val isLinearLayout: Boolean = true,
) :
    ListAdapter<ProductEntity, ProductsListAdapter.MyViewHolder>(HomeListCallBack()) {
    /**
     * Used for controlling the home screen list.
     */
    class MyViewHolder(itemView: View) : ViewHolder(itemView) {

        val cardView: CardView = itemView.findViewById(R.id.ListCard)
        private val imageView: ImageView = itemView.findViewById(R.id.item_image)
        private val name: TextView = itemView.findViewById(R.id.item_name)
        private val type: TextView = itemView.findViewById(R.id.item_type)
        private val price: TextView = itemView.findViewById(R.id.item_price)

        private val tax: TextView = itemView.findViewById(R.id.item_tax)
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
            // buildString is safer to use in text assigning.
            type.text = buildString {
                append("Type: ")
                append(entity.productType.trim())
            }
            price.text = buildString {
                append("₹")
                append(entity.price.toString().toIndianNumberSystem())
            }
            tax.text = buildString {
                append("Tax: ")
                append(entity.tax)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layout = if (isLinearLayout) R.layout.list_item else R.layout.grid_item
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    fun submitNewList(list: List<ProductEntity>) {
        submitList(list)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.apply {
            val entity = currentList[position]
            bind(entity)
            cardView.setOnClickListener {
                listener.onClick(entity.id)
            }
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