package com.jalaljankhan.azdigitizer.patterns.abstracts

import android.view.ViewGroup
import android.view.WindowInsets
import com.jalaljankhan.azdigitizer.cutout.DeviceCutout
import com.jalaljankhan.azdigitizer.models.DeviceScreenSize
import com.jalaljankhan.azdigitizer.models.DrawIcon

internal interface Pattern {
    fun draw(
        icon: DrawIcon,
        displayNotchArea: Boolean,
        deviceCutout: DeviceCutout,
        deviceSize: DeviceScreenSize,
        rootViewWindowInsets: WindowInsets?
    )

    fun smash(x: Int, y: Int, viewGroup: ViewGroup, onCompletelySmashed: () -> Unit)
}