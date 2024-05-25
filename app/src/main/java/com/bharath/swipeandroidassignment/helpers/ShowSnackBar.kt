package com.bharath.swipeandroidassignment.helpers

import android.content.Context
import android.view.View
import com.bharath.swipeandroidassignment.R
import com.google.android.material.snackbar.Snackbar

/**
 * Used for Showing the snackbars.
 */
class ShowSnackBar(private val context: Context) {


    fun showErrorSnack(view: View, content: String) {
        Snackbar.make(view, content, Snackbar.LENGTH_LONG)
            .setBackgroundTint(context.resources.getColor(R.color.md_theme_errorContainer))
            .setTextColor(context.resources.getColor(R.color.md_theme_error))
            .show()
    }
}