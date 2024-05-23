package com.bharath.swipeandroidassignment.data.repo.local

import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.data.source.local.ProductsDao
import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductsRepositoryImpl(
    private val dao: ProductsDao,
) : ProductsRepository {
    override suspend fun upsertProductEntity(entity: ProductEntity) {
        withContext(Dispatchers.IO) {
            dao.upsertProductEntity(entity)
        }

    }

    override suspend fun upsertProductEntityList(entities: List<ProductEntity>) {
        withContext(Dispatchers.IO) {
            dao.upsertProductEntityList(entities)
        }
    }

    override suspend fun deleteProductEntity(entity: ProductEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteProductEntity(entity)
        }
    }

    override suspend fun getProductEntity(id: String): Flow<ProductEntity> {
        return withContext(Dispatchers.IO) {
            dao.getProductEntity(id)
        }
    }

    override suspend fun getAllProductEntityList(): Flow<List<ProductEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getAllProductEntityList()
        }
    }

    override suspend fun checkIfEmpty(): Flow<Int> {
        return withContext(Dispatchers.IO) {
            dao.checkIfEmpty()
        }
    }
}