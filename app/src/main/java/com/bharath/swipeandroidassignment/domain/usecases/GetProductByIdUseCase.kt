package com.bharath.swipeandroidassignment.domain.usecases

import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository

/**
 * @property GetProductByIdUseCase get product by room entity id use case
 */
class GetProductByIdUseCase(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(id: Int) = productsRepository.getProductEntity(id)
}