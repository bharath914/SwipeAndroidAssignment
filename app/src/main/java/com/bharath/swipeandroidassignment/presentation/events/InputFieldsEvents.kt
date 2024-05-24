package com.bharath.swipeandroidassignment.presentation.events

import android.content.Context
import android.net.Uri

sealed class InputFieldsEvents {
    data class OnProductNameEnter(val name: String) : InputFieldsEvents()
    data class OnProductPriceEnter(val price: String) : InputFieldsEvents()
    data class OnProductTypeEnter(val type: String) : InputFieldsEvents()
    data class OnTaxEnter(val tax: String) : InputFieldsEvents()
    data class OnFileAdd(val file: Uri) : InputFieldsEvents()
    data class OnFilesAdd(val files: List<Uri>) : InputFieldsEvents()

    data class OnSentClick(val context: Context) : InputFieldsEvents()
}