package com.jalaljankhan.azdigitizer.device


import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import com.jalaljankhan.azdigitizer.cutout.DeviceCutout
import com.jalaljankhan.azdigitizer.models.DeviceCutoutInset
import com.jalaljankhan.azdigitizer.models.DeviceScreenSize

internal class DeviceDisplayImpl : DeviceDisplay {

    override fun fitDisplayToFullScreen(context: Activity) {
        // Make the window fullscreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 (API level 30) and above
            context.window.setDecorFitsSystemWindows(false)
            context.window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars() or WindowInsets.Type.systemBars())
            context.window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // For older Android versions
            context.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
    }

    override fun deviceSize(context: Activity): DeviceScreenSize {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val width = windowManager?.currentWindowMetrics?.bounds?.width() ?: 0
            val height = windowManager?.currentWindowMetrics?.bounds?.height() ?: 0

            DeviceScreenSize(width = width, height = height)
        } else {
            val display = DisplayMetrics()
            windowManager?.defaultDisplay?.getRealMetrics(display)

            val width = display.widthPixels
            val height = display.heightPixels

            DeviceScreenSize(width = width, height = height)
        }
    }

    override fun deviceSize(
        context: Activity,
        waterfallInsets: DeviceCutoutInset,
        isNotchAreaDisplayed: Boolean,
        deviceCutout: DeviceCutout
    ): DeviceScreenSize {
        val displayOriginalSize = deviceSize(context = context)
        val waterfallWidth = waterfallInsets.left + waterfallInsets.right
        val waterfallHeight = waterfallInsets.top + waterfallInsets.bottom

        return if (waterfallWidth > 0 && waterfallHeight <= 0) {
            displayOriginalSize.copy(width = displayOriginalSize.width - waterfallWidth)
        } else {
            if (!isNotchAreaDisplayed) {
                displayOriginalSize.copy(
                    height = displayOriginalSize.height - deviceCutout.deviceNotchSize(
                        context = context
                    )
                )
            } else
                displayOriginalSize
        }
    }
}