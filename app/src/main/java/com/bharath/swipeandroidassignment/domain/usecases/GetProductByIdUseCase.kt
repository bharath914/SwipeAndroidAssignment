package com.bharath.swipeandroidassignment.domain.usecases

import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository

class GetProductByIdUseCase(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(id: Int) = productsRepository.getProductEntity(id)
}