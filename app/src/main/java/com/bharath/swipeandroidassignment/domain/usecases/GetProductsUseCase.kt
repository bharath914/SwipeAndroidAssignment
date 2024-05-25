package com.bharath.swipeandroidassignment.domain.usecases

import com.bharath.swipeandroidassignment.data.common.Resource
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import com.bharath.swipeandroidassignment.helpers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * @property GetProductByIdUseCase Used for getting the products as well as caching them.
 */
class GetProductsUseCase(
    private val remoteProductsRepo: RemoteProductsRepo,
    private val localProductsRepository: ProductsRepository,
) {

    operator fun invoke(isRefresh: Boolean = false): Flow<Resource<List<ProductEntity>>> = flow {
        try {
            // check if table is empty i.e, User has cleared the storage or launching for first time.
            val isEmpty = localProductsRepository.checkIfEmpty().filterNotNull().first() == 0
            // Basically I Want to show a different loading animation for not cached state .
            // but it is redundant.
            if (isEmpty) emit(Resource.NotCached())

            if (isEmpty || isRefresh) {
                // emits Loading to show a progress bar.
                emit(Resource.Loading())


                val result = remoteProductsRepo.getProducts()
                // gets the result from the swipe server.

                localProductsRepository.clearAllProducts()
                // clear the old cached data.
                val mapped = result.toEntity()
                /**
                 * @see toEntity()
                 * this function is needed to insert the data into the database.
                 */

                localProductsRepository.upsertProductEntityList(mapped)
                // after inserting into the database,

                val localCached =
                    localProductsRepository.getAllProductEntityList().filterNotNull().first()
                // we again fetch the cached data from the database.
                // this is must for getting the id's correctly.
                emit(Resource.Success(localCached))
                // then we emit the success.
            } else {
                val local =
                    localProductsRepository.getAllProductEntityList().filterNotNull().first()
                // if we already cached and no refresh required then we emit the cached data.
                emit(Resource.Success(local))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // emit error
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}