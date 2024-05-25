package com.bharath.swipeandroidassignment.presentation.fragments.dialog

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import com.bharath.swipeandroidassignment.domain.usecases.PostProductUseCase
import com.bharath.swipeandroidassignment.presentation.events.InputFieldsEvents
import com.bharath.swipeandroidassignment.presentation.states.InputFieldsUIState
import com.bharath.swipeandroidassignment.presentation.states.SendState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BottomSheetFragViewModel(
    private val postProductUseCase: PostProductUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InputFieldsUIState())
    val uiState = _uiState.asStateFlow()
    private val _imageList = MutableStateFlow(emptyList<Uri>())
    val imageList = _imageList.asStateFlow()

    private val _price = MutableStateFlow("")
    val price = _price.asStateFlow()

    private val _sendState = MutableStateFlow(SendState<SentResponse>())
    val sendState = _sendState.asStateFlow()
    fun onEvent(events: InputFieldsEvents) {
        viewModelScope.launch {
            when (events) {
                is InputFieldsEvents.OnFileAdd -> {
                    _imageList.update {
                        it + events.file
                    }
                }

                is InputFieldsEvents.OnFilesAdd -> {

                    _imageList.update {
                        it + events.files
                    }
                }

                is InputFieldsEvents.OnProductNameEnter -> {
                    _uiState.update {
                        it.copy(productName = events.name)
                    }
                }

                is InputFieldsEvents.OnProductPriceEnter -> {
                    _uiState.update { it.copy(price = events.price) }
                    _price.update { events.price }

                }

                is InputFieldsEvents.OnProductTypeEnter -> {
                    _uiState.update {
                        it.copy(productType = events.type)
                    }
                }

                is InputFieldsEvents.OnTaxEnter -> {
                    _uiState.update {
                        it.copy(tax = events.tax)
                    }
                }

                is InputFieldsEvents.OnSentClick -> {
                    viewModelScope.launch(IO) {

                        if (blankChecker(context = events.context).not()) {

                            if (_uiState.value.tax.toFloat() <= 100f) {

                                postProductUseCase(
                                    _uiState.value.copy(files = _imageList.value),
                                    events.context
                                ).onEach { state ->
                                    _sendState.update { state }

                                }.collect()
                            } else {
                                launch(Main) {
                                    Toast.makeText(
                                        events.context,
                                        "Tax Should be <=100% ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }

                is InputFieldsEvents.RemoveImage -> {
                    val updated = _imageList.value.toMutableList()
                    updated.removeAt(events.index)
                    _imageList.update {
                        updated
                    }
                }
            }
        }

    }


    private fun blankChecker(context: Context): Boolean {
        return if (_uiState.value.productName.isBlank()) {
            showToast(context, "Product Name Should Not Be Blank!!")
            true
        } else if (_uiState.value.productType.isBlank()) {
            showToast(context, "Product Type is Mandatory")
            true
        } else if (_uiState.value.price.isBlank()) {
            showToast(context, "Price is Mandatory")
            true
        } else if (_uiState.value.tax.isBlank()) {
            showToast(context, "Please Enter Tax Percentage")
            true
        } else false
    }


    private fun showToast(context: Context, text: String) {
        viewModelScope.launch(Main) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}