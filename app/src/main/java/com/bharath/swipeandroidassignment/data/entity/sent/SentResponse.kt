package com.bharath.swipeandroidassignment.data.entity.sent


import androidx.annotation.Keep
import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property SentResponse : useful for getting the sent response from the server
 */
@Keep
@Serializable
data class SentResponse(
    @SerialName("message") val message: String,
    @SerialName("product_details") val productDetails: ProductsItem,
    @SerialName("product_id") val productId: Int,
    @SerialName("success") val success: Boolean,
)