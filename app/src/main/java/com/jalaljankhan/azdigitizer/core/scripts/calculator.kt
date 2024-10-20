package com.jalaljankhan.azdigitizer.core.scripts

import android.content.Context

// Helper function to convert dp to pixels
internal fun Int.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

// Helper function to convert dimens to pixels
internal fun Int.dpiToPx(context: Context): Int {
    return context.resources.getDimensionPixelSize(this)
}

// Helper function to convert pixels to dp
internal fun Int.toDp(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this / density).toInt()
}