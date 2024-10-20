package com.jalaljankhan.azdigitizer

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import com.jalaljankhan.azdigitizer.core.enums.PatternShape
import com.jalaljankhan.azdigitizer.cutout.DeviceCutout
import com.jalaljankhan.azdigitizer.cutout.DeviceCutoutImpl
import com.jalaljankhan.azdigitizer.device.DeviceDisplayImpl
import com.jalaljankhan.azdigitizer.models.DrawIcon
import com.jalaljankhan.azdigitizer.patterns.abstracts.Pattern
import com.jalaljankhan.azdigitizer.patterns.builder.PatternBuilder


class DigitizerSdk(private val context: Activity) {
    private lateinit var mPattern: Pattern
    private var mRootViewWindowInsets: WindowInsets? = null
    private val mPatternBuilder by lazy { PatternBuilder(context) }

    fun draw(
        pattern: PatternShape,
        icon: Int,
        iconWidth: Int,
        iconHeight: Int,
        container: ViewGroup,
        displayNotchArea: Boolean,
    ): DigitizerSdk {
        mPattern = mPatternBuilder.build(pattern = pattern)
        context.window?.decorView?.let {
            registerViewInsets(windowDecorView = it)
        }

        val display = DeviceDisplayImpl()
        display.fitDisplayToFullScreen(context = context)

        val deviceCutout: DeviceCutout = DeviceCutoutImpl()

        // Display or Hide the Notch Area
        deviceCutout.displayNotchArea(context = context, displayArea = displayNotchArea)

        // Check for Waterfall Cutout
        val waterfallCutoutInset = deviceCutout.waterfallCutout(context = context)

        // Calculate Device's Display Size
        val deviceSize = display.deviceSize(
            context = context,
            waterfallInsets = waterfallCutoutInset,
            isNotchAreaDisplayed = displayNotchArea,
            deviceCutout = deviceCutout
        )

        // Draw Pattern
        mPattern
            .draw(
                icon = DrawIcon(
                    context = context,
                    drawableIcon = icon,
                    drawableWidth = iconWidth,
                    drawableHeight = iconHeight,
                    container = container
                ),
                deviceSize = deviceSize,
                deviceCutout = deviceCutout,
                displayNotchArea = displayNotchArea,
                rootViewWindowInsets = mRootViewWindowInsets
            )

        return this
    }

    fun smash(x: Int, y: Int, viewGroup: ViewGroup, onCompletelySmashed: () -> Unit) {
        mPattern.smash(
            x = x,
            y = y,
            viewGroup = viewGroup,
            onCompletelySmashed = onCompletelySmashed
        )
    }

    private fun registerViewInsets(windowDecorView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRootViewWindowInsets = windowDecorView.rootWindowInsets
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(windowDecorView) { _, insets ->
                mRootViewWindowInsets = insets.toWindowInsets()
                insets
            }
        }
    }
}