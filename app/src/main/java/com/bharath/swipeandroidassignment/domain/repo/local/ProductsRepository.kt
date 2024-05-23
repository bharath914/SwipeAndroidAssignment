package com.bharath.swipeandroidassignment.domain.repo.local

import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun upsertProductEntity(entity: ProductEntity)
    suspend fun upsertProductEntityList(entities: List<ProductEntity>)
    suspend fun deleteProductEntity(entity: ProductEntity)
    suspend fun checkIfEmpty(): Flow<Int>
    suspend fun getProductEntity(id: String): Flow<ProductEntity>
    suspend fun getAllProductEntityList(): Flow<List<ProductEntity>>
}