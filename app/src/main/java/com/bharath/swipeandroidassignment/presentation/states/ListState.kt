package com.bharath.swipeandroidassignment.presentation.states

/**
 * Represents the state of a list, including loading and error states.
 *
 * @param T The type of items in the list.
 * @property list The current list of items. Defaults to an empty list.
 * @property isNotCached Indicates if the list is not cached. Defaults to false.
 * @property isLoading Indicates if the list is currently loading data from the server. Defaults to false.
 * @property error An optional error message. Defaults to null.
 */
data class ListState<T>(
    val list: List<T> = emptyList(),
    val isNotCached: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)