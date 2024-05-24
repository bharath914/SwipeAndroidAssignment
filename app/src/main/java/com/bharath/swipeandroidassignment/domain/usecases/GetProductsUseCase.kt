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

class GetProductsUseCase(
    private val remoteProductsRepo: RemoteProductsRepo,
    private val localProductsRepository: ProductsRepository,
) {
    operator fun invoke(isRefresh: Boolean = false): Flow<Resource<List<ProductEntity>>> = flow {
        try {

            val isEmpty = localProductsRepository.checkIfEmpty().filterNotNull().first() == 0
            if (isEmpty) emit(Resource.NotCached())

            if (isRefresh) emit(Resource.Loading())
            if (isEmpty || isRefresh) {
                val result = remoteProductsRepo.getProducts()
                val mapped = result.toEntity()
                localProductsRepository.upsertProductEntityList(mapped)
                emit(Resource.Success(mapped))
            } else {
                val local =
                    localProductsRepository.getAllProductEntityList().filterNotNull().first()
                emit(Resource.Success(local))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}