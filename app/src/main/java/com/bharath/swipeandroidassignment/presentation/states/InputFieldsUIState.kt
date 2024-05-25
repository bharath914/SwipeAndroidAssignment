package com.bharath.swipeandroidassignment.presentation.states

import android.net.Uri
import androidx.annotation.Keep
import com.bharath.swipeandroidassignment.helpers.ProductCategories

/**
 * Represents the UI state for input fields in a form.
 *
 * @property productName The name of the product entered by the user. Defaults to an empty string.
 * @property productType The type of the product selected by the user. Defaults to the first product type in [ProductCategories].
 * @property price The price of the product entered by the user. Defaults to an empty string.
 * @property tax The tax amount entered by the user. Defaults to an empty string.
 * @property files The list of URIs of files added by the user. Defaults to an empty list.
 */
@Keep
data class InputFieldsUIState(
    val productName: String = "",
    val productType: String = ProductCategories().productTypes.first(),
    val price: String = "",
    val tax: String = "",
    val files: List<Uri> = arrayListOf(),
)
