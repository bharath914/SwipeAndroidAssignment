package com.bharath.swipeandroidassignment.data.repo.remote

import android.util.Log
import com.bharath.swipeandroidassignment.data.entity.products.ProductsItem
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import com.bharath.swipeandroidassignment.data.source.remote.ProductsApi
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    override suspend fun postProduct(productsItem: ProductsItem): SentResponse {
        return withContext(Dispatchers.IO) {
            api.add(productsItem)
        }
    }
}