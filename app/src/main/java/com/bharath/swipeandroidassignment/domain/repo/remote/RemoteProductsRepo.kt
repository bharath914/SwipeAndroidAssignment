package com.bharath.swipeandroidassignment.domain.repo.remote

import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteProductsRepo {
    suspend fun getProducts(): List<ProductsItem>
    suspend fun postProduct(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        files: List<MultipartBody.Part>,
    ): SentResponse
}