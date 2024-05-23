package com.bharath.swipeandroidassignment.helpers

import androidx.annotation.Keep
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.data.entity.products.Products
import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem

@Keep
fun Products.toEntity() = map {
    ProductEntity(
        image = it.image,
        price = it.price,
        productName = it.product_name,
        productType = it.product_type,
        tax = it.tax
    )
}

fun List<ProductsItem>.toEntity() = map {
    ProductEntity(
        image = it.image,
        price = it.price, productName = it.product_name,
        productType = it.product_type,
        tax = it.tax
    )
}