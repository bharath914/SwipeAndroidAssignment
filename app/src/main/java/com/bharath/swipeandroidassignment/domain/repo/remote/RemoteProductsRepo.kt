package com.bharath.swipeandroidassignment.domain.repo.remote

import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse

interface RemoteProductsRepo {
    suspend fun getProducts(): List<ProductsItem>
    suspend fun postProduct(productsItem: ProductsItem): SentResponse
}