package com.bharath.swipeandroidassignment.presentation.events

import android.content.Context
import android.net.Uri

/**
 * Represents different events related to input fields in a form.
 */
sealed class InputFieldsEvents {

    /**
     * Triggered when the product name is entered.
     *
     * @property name The entered product name.
     */
    data class OnProductNameEnter(val name: String) : InputFieldsEvents()

    /**
     * Triggered when the product price is entered.
     *
     * @property price The entered product price.
     */
    data class OnProductPriceEnter(val price: String) : InputFieldsEvents()

    /**
     * Triggered when the product type is entered.
     *
     * @property type The entered product type.
     */
    data class OnProductTypeEnter(val type: String) : InputFieldsEvents()

    /**
     * Triggered when the tax is entered.
     *
     * @property tax The entered tax amount.
     */
    data class OnTaxEnter(val tax: String) : InputFieldsEvents()

    /**
     * Triggered when a single file is added.
     *
     * @property file The URI of the added file.
     */
    data class OnFileAdd(val file: Uri) : InputFieldsEvents()

    /**
     * Triggered when multiple files are added.
     *
     * @property files A list of URIs of the added files.
     */
    data class OnFilesAdd(val files: List<Uri>) : InputFieldsEvents()

    /**
     * Triggered when the send button is clicked.
     *
     * @property context The context in which the event is triggered.
     */
    data class OnSentClick(val context: Context) : InputFieldsEvents()

    /**
     * Triggered when an image is removed by index.
     *
     * @property index The index of the image to be removed.
     */
    data class RemoveImage(val index: Int) : InputFieldsEvents()
}
