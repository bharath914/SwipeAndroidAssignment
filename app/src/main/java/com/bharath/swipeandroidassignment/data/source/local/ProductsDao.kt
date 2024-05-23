package com.bharath.swipeandroidassignment.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Upsert
    suspend fun upsertProductEntity(entity: ProductEntity)

    @Upsert
    suspend fun upsertProductEntityList(entities: List<ProductEntity>)

    @Delete
    suspend fun deleteProductEntity(entity: ProductEntity)

    @Query("select * from ProductEntity where id = :id")
    fun getProductEntity(id: String): Flow<ProductEntity>

    @Query("select * from ProductEntity")
    fun getAllProductEntityList(): Flow<List<ProductEntity>>

    @Query("select Count(*) from ProductEntity ")
    fun checkIfEmpty(): Flow<Int>
}
