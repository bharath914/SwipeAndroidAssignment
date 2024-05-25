package com.bharath.swipeandroidassignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity

@Database(
    entities = [ProductEntity::class], version = 1
)
/**
 * @property AppDatabase : RoomDatabase() Used for local Caching
 * @property productsDao : ProductsDao Used for local Caching of [ProductEntity]
 */
abstract class AppDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao

}