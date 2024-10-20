package com.jalaljankhan.azdigitizer.cutout

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import com.jalaljankhan.azdigitizer.core.scripts.dpiToPx
import com.jalaljankhan.azdigitizer.models.DeviceCutoutInset

internal class DeviceCutoutImpl : DeviceCutout {
    override fun deviceNotchSize(context: Activity): Int {
        var size = 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val insets = windowManager?.currentWindowMetrics?.windowInsets

                if (insets != null) {
                    val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                    size = statusBarInsets.top
                }
            } else {
                val rect = Rect()
                windowManager?.defaultDisplay?.getRectSize(rect)
                size = rect.top
            }
        }

        if (size <= 0) {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                size = resourceId.dpiToPx(context)
                return size
            }
        }

        return size
    }

    override fun waterfallCutout(context: Activity): DeviceCutoutInset {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return DeviceCutoutInset(
                left = -1,
                top = -1,
                right = -1,
                bottom = -1
            ) // doesn't support Waterfall
        } else {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            val display = windowManager?.defaultDisplay

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val waterfallInsets = display?.cutout?.waterfallInsets
                if (waterfallInsets == null) {
                    DeviceCutoutInset(left = 0, top = 0, right = 0, bottom = 0)
                } else {
                    DeviceCutoutInset(
                        left = waterfallInsets.left,
                        top = waterfallInsets.top,
                        right = waterfallInsets.right,
                        bottom = waterfallInsets.bottom
                    )
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (display?.cutout?.safeInsetTop == 0) {
                    val left = display.cutout?.safeInsetLeft ?: 0
                    val right = display.cutout?.safeInsetRight ?: 0

                    val top = display.cutout?.safeInsetTop ?: 0
                    val bottom = display.cutout?.safeInsetBottom ?: 0

                    DeviceCutoutInset(left = left, top = top, right = right, bottom = bottom)
                } else {
                    DeviceCutoutInset(left = 0, top = 0, right = 0, bottom = 0)
                }
            } else {
                DeviceCutoutInset(left = 0, top = 0, right = 0, bottom = 0)
            }
        }
    }

    override fun displayNotchArea(context: Activity, displayArea: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.window.attributes.layoutInDisplayCutoutMode =
                if (displayArea) {
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                } else {
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
                }
        }
    }

    override fun isNotchFoundAt(
        rootViewWindowInsets: WindowInsets?,
        row: Int,
        col: Int,
        iconWidth: Int,
        iconHeight: Int
    ): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            return false

        val boundaries =
            rootViewWindowInsets?.displayCutout?.boundingRects

        val cell = Rect(
            col * iconWidth,
            row * iconHeight,
            (col + 1) * iconWidth,
            (row + 1) * iconHeight
        )

        return boundaries?.find { rectangle ->
            rectangle.intersect(cell)
        } != null
    }

}