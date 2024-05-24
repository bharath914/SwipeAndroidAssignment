package com.bharath.swipeandroidassignment.presentation.states

data class SendState<T>(
    val sentData: T? = null,
    val isSending: Boolean = false,
    val error: String? = null,
)