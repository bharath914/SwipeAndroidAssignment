package com.bharath.swipeandroidassignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity

@Database(
    entities = [ProductEntity::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao

    fun getProductDao() = productsDao
}