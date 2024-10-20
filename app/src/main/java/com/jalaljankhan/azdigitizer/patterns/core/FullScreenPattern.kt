package com.jalaljankhan.azdigitizer.patterns.core

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import com.jalaljankhan.azdigitizer.patterns.abstracts.PatternImpl

internal class FullScreenPattern(context: Activity) : PatternImpl(context) {
    override fun drawPattern(
        image: ImageView,
        row: Int,
        rows: Int,
        column: Int,
        columns: Int,
        container: ViewGroup
    ) {
        container.addView(image)
    }
}