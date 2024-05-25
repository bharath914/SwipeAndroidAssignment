package com.bharath.swipeandroidassignment.presentation.fragments.productDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.domain.usecases.GetProductByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _productEntity = MutableStateFlow(ProductEntity())
    val productEntity = _productEntity.asStateFlow()

    init {
        savedStateHandle.get<Int>("Id")?.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                getProductByIdUseCase(id)
                    .onEach { entity ->
                        _productEntity.update { entity }
                    }
                    .collect()
            }
        }
    }
}