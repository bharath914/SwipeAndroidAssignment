package com.bharath.swipeandroidassignment

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bharath.swipeandroidassignment.helpers.PermissionCheckers
import com.bharath.swipeandroidassignment.helpers.isNetworkAvailable
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        noInternetDialog()
        val permissionCheckers = PermissionCheckers()
        permissionCheckers.launchPermission(this, this)

        // show the splash screen exit animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // goes up
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                ).apply {
                    duration = 600
                    doOnEnd {
                        splashScreenView.remove()
                    }
                }.also {
                    it.start()
                }
            }
        }

    }

    private fun noInternetDialog() {
        // show this dialog when not connected to the internet.
        if (isNetworkAvailable(this).not()) {

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.no_internet_view)
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            lifecycleScope.launch {
                dialog.show()
            }
        }
    }


}