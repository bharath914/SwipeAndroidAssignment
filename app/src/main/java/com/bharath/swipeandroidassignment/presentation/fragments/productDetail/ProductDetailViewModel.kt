package com.bharath.swipeandroidassignment.presentation.fragments.productDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.domain.usecases.GetProductByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
) : ViewModel() {

    private val _productEntity = MutableStateFlow<ProductEntity?>(null)
    val productEntity = _productEntity.asStateFlow()

    fun getData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductByIdUseCase(id)
                .collectLatest { entity ->
                    Log.d("Entity", "ViewModel getData:$entity ")
                    _productEntity.emit(entity)
                    Log.d(
                        "Entity",
                        "ViewModel msf value ----------->>>>" + _productEntity.value.toString()
                    )
                }
        }
    }

}

