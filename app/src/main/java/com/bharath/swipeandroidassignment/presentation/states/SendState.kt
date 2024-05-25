package com.bharath.swipeandroidassignment.presentation.states

/**
 * Represents the state of a sending operation, including success and error states.
 *
 * @param T The type of data being sent.
 * @property sentData The data that has been sent. Defaults to null.
 * @property isSending Indicates if the data is currently being sent. Defaults to false.
 * @property error An optional error message. Defaults to null.
 */
data class SendState<T>(
    val sentData: T? = null,
    val isSending: Boolean = false,
    val error: String? = null,
)