package com.bharath.swipeandroidassignment.data.entity.products


import androidx.annotation.Keep
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Products Item to convert JSON Response to a kotlin dataclass.
 */
@Keep
@Serializable
data class ProductsItem(
    @SerialName("image") val image: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("product_name") val product_name: String = "",
    @SerialName("product_type") val product_type: String = "",
    @SerialName("tax") val tax: Double = 0.0,
)

/**
 * Extensive Function to Convert Products Item to Product Entity
 */
fun ProductsItem.toEntity() = ProductEntity(
    image = image,
    price = price,
    productName = product_name,
    productType = product_type,
    tax = tax
)