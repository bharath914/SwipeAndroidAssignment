package com.bharath.swipeandroidassignment.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionCheckers {
    fun checkPermissionImagePermssion(context: Context): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.READ_EXTERNAL_STORAGE
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun launchPermission(context: Context, activity: AppCompatActivity) {
        if (checkPermissionImagePermssion(context).not()) {
            val permissionActivity =
                activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        Toast.makeText(
                            context,
                            "Hurray! U can Upload Images Now",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "Oops Permission Not granted Cannot Upload Images",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionActivity.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                permissionActivity.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        }
    }

    fun launchPermissionInFragment(fragment: Fragment, context: Context) {
        if (checkPermissionImagePermssion(context).not()) {
            val permissionActivity =
                fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        Toast.makeText(
                            context,
                            "Hurray! U can Upload Images Now",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "Oops Permission Not granted Cannot Upload Images",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionActivity.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                permissionActivity.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        }
    }

}