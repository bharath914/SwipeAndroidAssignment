package com.bharath.swipeandroidassignment.presentation.fragments.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.helpers.Validators
import com.bharath.swipeandroidassignment.helpers.toIndianNumberSystem
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var backBtn: ImageView
    private lateinit var imageView: ImageView
    private lateinit var productName: TextView
    private lateinit var productTax: TextView
    private lateinit var productType: TextView
    private lateinit var productPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_product_detail, container, false)
        bind(v)

        val id = arguments?.getInt("Id") ?: 1
        Log.d("Nav Id", "onCreateView: The passed Id is $id")
        viewModel.getData(id)

        // collect the flow and set the values
        lifecycleScope.launch {
            viewModel.productEntity.filterNotNull().collectLatest { entity: ProductEntity ->
                Log.d("Entity", "Got the entity $entity ")
                setValues(entity)
            }
        }
        // navigate back
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return v
    }

    // set the values to the views.
    private fun setValues(entity: ProductEntity) {
        Log.d("Entity", "setValues: $entity ")
        Glide.with(requireContext())
            .load(
                if (Validators.isImageUrlValid(entity.image)) entity.image else R.drawable.picture
            )
            .centerCrop()
            .error(R.drawable.picture)
            .into(imageView)

        productTax.text =
            buildString {
                append("tax: ")
                append(entity.tax)
            }
        productName.text = entity.productName
        productType.text =
            buildString {
                append("Type: ")
                append(entity.productType)
            }
        productPrice.text = buildString {
            append("₹")
            append(entity.price.toString().toIndianNumberSystem())
        }
    }

    // bind the required components
    private fun bind(v: View) {
        backBtn = v.findViewById(R.id.pd_backBtn)
        imageView = v.findViewById(R.id.pd_image)
        productName = v.findViewById(R.id.pd_name)
        productTax = v.findViewById(R.id.pd_tax)
        productType = v.findViewById(R.id.pd_type)
        productPrice = v.findViewById(R.id.pd_price)
    }

}