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
    /**
     * [checkPermissionImagePermssion] used to check permission to upload images.
     */
    fun checkPermissionImagePermssion(context: Context): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.READ_EXTERNAL_STORAGE
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkNotificationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            return ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } else return true
    }

    fun launchNotificationPermission(context: Context, activity: AppCompatActivity) {
        if (checkNotificationPermission(context).not()) {
            val permissionActivity =
                activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                    } else {
                        Toast.makeText(
                            context,
                            "Grant Notification Permission For Posting Notifications",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            permissionActivity.launch(Manifest.permission.POST_NOTIFICATIONS)

        }
    }

    /**
     *[launchPermission] launches the permission dialog.
     *
     * */
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