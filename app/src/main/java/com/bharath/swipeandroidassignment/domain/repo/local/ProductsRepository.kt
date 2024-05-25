package com.bharath.swipeandroidassignment.domain.repo.local

import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import kotlinx.coroutines.flow.Flow


/**
 * This Pattern is very useful for testing the unit test cases.
 * Simply We can implement this Repository to a FakeTestingRepository and check all the methods.
 */
interface ProductsRepository {
    suspend fun upsertProductEntity(entity: ProductEntity)
    suspend fun upsertProductEntityList(entities: List<ProductEntity>)
    suspend fun deleteProductEntity(entity: ProductEntity)
    suspend fun checkIfEmpty(): Flow<Int>
    suspend fun clearAllProducts()
    suspend fun getProductEntity(id: Int): Flow<ProductEntity>
    suspend fun getAllProductEntityList(): Flow<List<ProductEntity>>
}