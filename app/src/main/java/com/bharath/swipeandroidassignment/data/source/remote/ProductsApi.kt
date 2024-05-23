package com.bharath.swipeandroidassignment.data.source.remote

import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApi {
    @GET("get")
    suspend fun get(): List<ProductsItem>

    @POST("add")
    suspend fun add(@Body productsItem: ProductsItem): SentResponse
}