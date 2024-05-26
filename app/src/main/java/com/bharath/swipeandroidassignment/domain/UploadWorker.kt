package com.bharath.swipeandroidassignment.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bharath.swipeandroidassignment.domain.repo.remote.RemoteProductsRepo
import com.bharath.swipeandroidassignment.helpers.FileUtils
import com.bharath.swipeandroidassignment.helpers.NotificationUtil
import com.bharath.swipeandroidassignment.helpers.PermissionCheckers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadWorker(
    private val remoteProductsRepo: RemoteProductsRepo,
    private val context: Context,
    workParam: WorkerParameters,
) : CoroutineWorker(context, workParam) {

    override suspend fun doWork(): Result {

        try {

            val productName = inputData.getString(WorkerCons.productName)
            val productType = inputData.getString(WorkerCons.productType)
            val productPrice = inputData.getString(WorkerCons.price)
            val productTax = inputData.getString(WorkerCons.tax)
            val productUris = inputData.getStringArray(WorkerCons.listOfUris)

            if (productName != null && productType != null && productPrice != null && productTax != null && productUris != null) {

                val notification = NotificationUtil(context)
                val permissionCheckers = PermissionCheckers()
                if (permissionCheckers.checkNotificationPermission(context)) {
                    notification.postUploadNotification(
                        productName.length,
                        "Uploading..",
                        "Uploading ${productName} to the swipe server don't turn off the internet. "
                    )
                }
                val pName = productName.toRequestBody("text/plain".toMediaTypeOrNull())
                val pPrice =
                    productPrice.replace(",", "").toRequestBody("text/plain".toMediaTypeOrNull())
                val tax = productTax.toRequestBody("text/plain".toMediaTypeOrNull())
                val type = productType.toRequestBody("text/plain".toMediaTypeOrNull())
                val fileUris = productUris.map { Uri.parse(it) }
                // transform the given files Media into the Request Body.
                val fileParts = fileUris.map {
                    val file = File(FileUtils.getPath(context, it))
                    val requestFile = file
                        .asRequestBody(context.contentResolver.getType(it)
                            ?.let { it1 -> it1.toMediaTypeOrNull() })

                    //create the Multipart from the given Uri's.
                    MultipartBody.Part.createFormData("files[]", file.name, requestFile)
                }
                val sendState = remoteProductsRepo.postProduct(
                    productName = pName,
                    productType = type,
                    price = pPrice,
                    tax = tax,
                    files = fileParts
                )
                // if it's success then close the bottom sheet.
                if (sendState.success) {
                    Log.d("Product", "invoke: $sendState ")
                    notification.cancelNotification(productName.length)
                    notification.postSuccessNotification("Successful!! Please Refresh the List")
                    return Result.success()
                } else {
                    return Result.retry()
                }


            } else {
                return Result.failure()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }

    }
}

object WorkerCons {
    const val productName: String = "productName"
    const val productType: String = "productType"
    const val price: String = "price"
    const val tax: String = "tax"
    const val listOfUris = "ListOfUris"

}