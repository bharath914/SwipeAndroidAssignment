package com.bharath.swipeandroidassignment.presentation.fragments.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
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
        lifecycleScope.launch {
            viewModel.productEntity.collectLatest { entity: ProductEntity ->
                setValues(entity)
            }
        }
        return v
    }

    private fun setValues(entity: ProductEntity) {
        Glide.with(requireContext())
            .load(entity.image)
            .error(R.drawable.picture)
            .into(imageView)
        productTax.text = "${entity.tax}"
        productName.text = entity.productName
        productType.text = entity.productType
        productPrice.text = "${entity.price}"
    }

    private fun bind(v: View) {
        imageView = v.findViewById(R.id.pd_image)
        productName = v.findViewById(R.id.pd_name)
        productTax = v.findViewById(R.id.pd_tax)
        productType = v.findViewById(R.id.pd_type)
        productPrice = v.findViewById(R.id.pd_price)
    }

}