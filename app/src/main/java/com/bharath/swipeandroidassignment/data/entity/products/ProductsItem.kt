package com.bharath.swipeandroidassignment.data.entity.products


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductsItem(
    @SerialName("image") val image: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("product_name") val product_name: String = "",
    @SerialName("product_type") val product_type: String = "",
    @SerialName("tax") val tax: Double = 0.0,
)