package com.bharath.swipeandroidassignment.presentation.states

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class InputFieldsUIState(
    val productName: String = "",
    val productType: String = "",
    val price: String = "",
    val tax: String = "",
    val files: List<Uri> = arrayListOf(),
)