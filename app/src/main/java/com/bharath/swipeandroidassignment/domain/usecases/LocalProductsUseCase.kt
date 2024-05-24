package com.bharath.swipeandroidassignment.domain.usecases

import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository

class LocalProductsUseCase(
    private val repository: ProductsRepository,
) {
    suspend operator fun invoke() = repository.getAllProductEntityList()
}