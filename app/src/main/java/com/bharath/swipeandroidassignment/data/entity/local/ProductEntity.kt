package com.bharath.swipeandroidassignment.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Local Entity That is Stored in Room Database
 */
@Entity
@Serializable
data class ProductEntity(
    @SerialName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("image") val image: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("product_name") val productName: String = "",
    @SerialName("product_type") val productType: String = "",
    @SerialName("tax") val tax: Double = 0.0,
)

fun ProductEntity.doesMatchesQuery(query: String): Boolean {
    val combinations = listOf(
        productName,
        productType,
        price.toString()
    )
    return combinations.any {
        it.contains(query, true)
    }
}