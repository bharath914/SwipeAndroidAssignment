package com.bharath.swipeandroidassignment.domain.usecases

import android.content.Context
import android.util.Log
import com.bharath.swipeandroidassignment.data.entity.sent.SentResponse
import com.bharath.swipeandroidassignment.domain.repo.local.ProductsRepository
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import com.bharath.swipeandroidassignment.helpers.FileUtils
import com.bharath.swipeandroidassignment.presentation.states.InputFieldsUIState
import com.bharath.swipeandroidassignment.presentation.states.SendState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PostProductUseCase(
    private val remoteProductsRepo: RemoteProductsRepo,
    private val localRepository: ProductsRepository,
) {
    suspend operator fun invoke(
        uiState: InputFieldsUIState,
        context: Context,
    ): Flow<SendState<SentResponse>> = flow {
        try {
            emit(SendState(isSending = true))
            // transform the given inputs into the Request Body.
            val productName = uiState.productName.toRequestBody("text/plain".toMediaTypeOrNull())
            val productPrice =
                uiState.price.replace(",", "").toRequestBody("text/plain".toMediaTypeOrNull())
            val tax = uiState.tax.toRequestBody("text/plain".toMediaTypeOrNull())
            val type = uiState.productType.toRequestBody("text/plain".toMediaTypeOrNull())

            // transform the given files Media into the Request Body.
            val fileParts = uiState.files.map {
                val file = File(FileUtils.getPath(context, it))
                val requestFile = file
                    .asRequestBody(context.contentResolver.getType(it)
                        ?.let { it1 -> it1.toMediaTypeOrNull() })

                //create the Multipart from the given Uri's.
                MultipartBody.Part.createFormData("files[]", file.name, requestFile)
            }
            // get the send state after posting to the server.
            val sendState = remoteProductsRepo.postProduct(
                productName = productName,
                productType = type,
                price = productPrice,
                tax = tax,
                files = fileParts
            )
            // if it's success then close the bottom sheet.
            if (sendState.success) {
                Log.d("Product", "invoke: $sendState ")
            }
            emit(SendState(sentData = sendState))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(SendState(error = e.localizedMessage ?: "Unexpected Error"))
        }
    }
}