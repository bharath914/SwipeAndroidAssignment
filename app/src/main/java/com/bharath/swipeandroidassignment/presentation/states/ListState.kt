package com.bharath.swipeandroidassignment.presentation.states

data class ListState<T>(
    val list: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)