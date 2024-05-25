package com.bharath.swipeandroidassignment.presentation.fragments.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.common.Resource
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.domain.usecases.GetProductsUseCase
import com.bharath.swipeandroidassignment.helpers.isNetworkAvailable
import com.bharath.swipeandroidassignment.presentation.states.ListState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _productsListState = MutableStateFlow(ListState<ProductEntity>())
    val productsListState = _productsListState.asStateFlow()


    private val _connectedToNet = MutableStateFlow(true)
    val connectedToNet = _connectedToNet.asStateFlow()

    private val isCalled = MutableStateFlow(false)


    fun resetIsCalled() {
        isCalled.value = false
    }

    fun getData(isRefresh: Boolean = false, context: Context) {
        if (isCalled.value.not()) {


            viewModelScope.launch(IO) {
                val connected = isNetworkAvailable(context)
                if (!connected) {
                    _connectedToNet.update { false }
                }

                getProductsUseCase(isRefresh && connected).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _productsListState.update {
                                ListState(error = resource.message)
                            }
                        }

                        is Resource.Loading -> {
                            _productsListState.update {
                                ListState(isLoading = true)
                            }
                        }

                        is Resource.Success -> {
                            _productsListState.update {
                                Log.d("Products", "got the : ${resource.data} ")
                                ListState(list = resource.data ?: emptyList())

                            }
                        }

                        is Resource.NotCached -> {
                            _productsListState.update {
                                ListState(isNotCached = true)
                            }
                        }

                    }
                }.collect()
            }.invokeOnCompletion {
                isCalled.value = true
            }
        }

    }
}