package com.bharath.swipeandroidassignment.presentation.fragments.dialog

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import com.bharath.swipeandroidassignment.domain.usecases.PostProductUseCase
import com.bharath.swipeandroidassignment.presentation.events.InputFieldsEvents
import com.bharath.swipeandroidassignment.presentation.states.InputFieldsUIState
import com.bharath.swipeandroidassignment.presentation.states.SendState
import kotlinx.coroutines.Dispatchers.IO
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

    private val _sendState = MutableStateFlow(SendState<SentResponse>())
    val sendState = _sendState.asStateFlow()
    fun onEvent(events: InputFieldsEvents) {
        viewModelScope.launch(IO) {
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
                    _uiState.update {
                        it.copy(price = events.price)
                    }
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
                    launch(IO) {
                        postProductUseCase(
                            _uiState.value.copy(files = _imageList.value),
                            events.context
                        ).onEach { state ->
                            _sendState.update { state }

                        }.collect()
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


}