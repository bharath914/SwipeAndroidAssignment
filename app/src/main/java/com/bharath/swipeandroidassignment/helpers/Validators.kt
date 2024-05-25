package com.bharath.swipeandroidassignment.helpers

object Validators {
    fun isImageUrlValid(imageUrl: String): Boolean {
        return imageUrl.startsWith("https")
    }
}