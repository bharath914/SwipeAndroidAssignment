package com.bharath.swipeandroidassignment.data.source.remote

import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @property ProductsApi interface for Retrofit Api calls
 */
interface ProductsApi {
    /**
     * @return list of products from the Swipe Server.
     */
    @GET("get")
    suspend fun get(): List<ProductsItem>

    /**
     * @param files list of images to be uploaded.
     * @return [SentResponse] to check if the request was successful.
     */
    @POST("add")
    @Multipart
    suspend fun add(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part files: List<MultipartBody.Part>,
    ): SentResponse

}