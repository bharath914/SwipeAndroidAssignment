package com.bharath.swipeandroidassignment.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * @property ProductsDao used for accessing the ProductEntity Table in the [AppDatabase]
 */
@Dao
interface ProductsDao {
    /**
     * @param entity ProductEntity to be inserted
     *
     * [upsertProductEntity] is used to insert or update the [entity]
     */
    @Upsert
    suspend fun upsertProductEntity(entity: ProductEntity)

    /**
     * @param entities ProductEntity to be inserted
     *
     * [upsertProductEntity] is used to insert or update the list of  [entities]
     */
    @Upsert
    suspend fun upsertProductEntityList(entities: List<ProductEntity>)

    /**
     * @param entity ProductEntity to be deleted
     *
     * [deleteProductEntity] is used to delete the [entity]
     */
    @Delete
    suspend fun deleteProductEntity(entity: ProductEntity)


    /**
     * @param id id of the ProductEntity to be fetched
     */
    @Query("select * from ProductEntity where id = :id")
    fun getProductEntity(id: Int): Flow<ProductEntity>

    /**
     * @return list of ProductEntity
     */
    @Query("select * from ProductEntity")
    fun getAllProductEntityList(): Flow<List<ProductEntity>>

    /**
     * @return if the table is empty or not (no of rows)
     */
    @Query("select Count(*) from ProductEntity ")
    fun checkIfEmpty(): Flow<Int>

    /**
     * Clears EveryRow in the Table.
     */

    @Query("delete from ProductEntity")
    fun deleteEverything()
}
