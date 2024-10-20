package com.jalaljankhan.azdigitizer.models

import android.content.Context
import android.view.ViewGroup
import com.jalaljankhan.azdigitizer.core.scripts.dpiToPx

internal data class DrawIcon(
    val context: Context,
    val drawableIcon: Int,
    val drawableWidth: Int,
    val drawableHeight: Int,
    val container: ViewGroup
) {
    val iconWidth: Int
        get() = drawableWidth.dpiToPx(context)

    val iconHeight: Int
        get() = drawableHeight.dpiToPx(context)
}