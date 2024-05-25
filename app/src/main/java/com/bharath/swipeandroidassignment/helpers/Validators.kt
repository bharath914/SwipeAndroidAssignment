package com.bharath.swipeandroidassignment.helpers

/**
 * Useful for validating the ImageUrl
 * @property isImageUrlValid function to check a url is valid or not.
 */
object Validators {
    fun isImageUrlValid(imageUrl: String?): Boolean {
        return imageUrl.isNullOrBlank().not() && imageUrl!!.startsWith("https")
    }
}