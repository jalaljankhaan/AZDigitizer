package com.jalaljankhan.azdigitizer.cutout

import android.app.Activity
import android.view.WindowInsets
import com.jalaljankhan.azdigitizer.models.DeviceCutoutInset

internal interface DeviceCutout {
    fun deviceNotchSize(context: Activity): Int
    fun waterfallCutout(context: Activity): DeviceCutoutInset
    fun displayNotchArea(context: Activity, displayArea: Boolean)
    fun isNotchFoundAt(
        rootViewWindowInsets: WindowInsets?,
        row: Int,
        col: Int,
        iconWidth: Int,
        iconHeight: Int
    ): Boolean
}