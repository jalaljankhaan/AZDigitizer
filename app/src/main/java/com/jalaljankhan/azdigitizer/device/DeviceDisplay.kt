package com.jalaljankhan.azdigitizer.device

import android.app.Activity
import com.jalaljankhan.azdigitizer.cutout.DeviceCutout
import com.jalaljankhan.azdigitizer.models.DeviceCutoutInset
import com.jalaljankhan.azdigitizer.models.DeviceScreenSize

internal interface DeviceDisplay {
    fun fitDisplayToFullScreen(context: Activity)
    fun deviceSize(context: Activity): DeviceScreenSize
    fun deviceSize(
        context: Activity,
        waterfallInsets: DeviceCutoutInset,
        isNotchAreaDisplayed: Boolean,
        deviceCutout: DeviceCutout
    ): DeviceScreenSize
}