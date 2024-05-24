package com.bharath.swipeandroidassignment.presentation.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.data.entity.local.doesMatchesQuery
import com.bharath.swipeandroidassignment.domain.usecases.LocalProductsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel(private val localProductsUseCase: LocalProductsUseCase) : ViewModel() {


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearch(text: String) {
        _searchText.update { text }
    }


    private val _initialList = MutableStateFlow(emptyList<ProductEntity>())


    init {
        viewModelScope.launch(IO) {

            localProductsUseCase().collectLatest { list ->
                _initialList.update { list }
            }
        }
    }

    val filteredItems = searchText.onEach {

    }.combine(_initialList) { text, list ->

        if (text.isEmpty()) {
            emptyList()
        } else {
            list.filter {
                it.doesMatchesQuery(text)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}