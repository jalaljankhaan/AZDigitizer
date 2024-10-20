package com.jalaljankhan.azdigitizer.patterns.letters

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import com.jalaljankhan.azdigitizer.patterns.abstracts.PatternImpl

internal class LPattern(val context: Activity) : PatternImpl(context = context) {
    override fun drawPattern(
        image: ImageView,
        row: Int,
        rows: Int,
        column: Int,
        columns: Int,
        container: ViewGroup
    ) {
        if(column == 0 || row == rows - 1) {
            container.addView(image)
        }
    }
}