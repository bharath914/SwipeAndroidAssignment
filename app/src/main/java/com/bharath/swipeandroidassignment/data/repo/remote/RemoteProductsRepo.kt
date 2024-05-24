package com.bharath.swipeandroidassignment.data.repo.remote

import android.util.Log
import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import com.bharath.swipeandroidassignment.data.source.remote.ProductsApi
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteProductsRepoImpl(
    private val api: ProductsApi,
) : RemoteProductsRepo {
    override suspend fun getProducts(): List<ProductsItem> {
        return withContext(Dispatchers.IO) {
            val products = api.get()
            Log.d("Products", "getProducts: $products ")
            products
        }
    }

    override suspend fun postProduct(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        files: List<MultipartBody.Part>,
    ): SentResponse {
        return withContext(Dispatchers.IO) {
            api.add(
                productName = productName,
                productType = productType,
                price = price,
                tax = tax,
                files = files
            )
        }
    }
}