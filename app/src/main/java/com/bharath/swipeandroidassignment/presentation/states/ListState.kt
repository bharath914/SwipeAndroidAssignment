package com.bharath.swipeandroidassignment.presentation.states

data class ListState<T>(
    val list: List<T> = emptyList(),
    val isNotCached: Boolean = false,
    val isLoading: Boolean = false,// load the data from server.
    val error: String? = null,
)