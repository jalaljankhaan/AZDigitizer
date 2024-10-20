package com.jalaljankhan.azdigitizer.patterns.abstracts

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.jalaljankhan.azdigitizer.cutout.DeviceCutout
import com.jalaljankhan.azdigitizer.core.scripts.dpiToPx
import com.jalaljankhan.azdigitizer.models.DeviceScreenSize
import com.jalaljankhan.azdigitizer.models.DrawIcon

internal abstract class PatternImpl(private val context: Activity) : Pattern {
    internal abstract fun drawPattern(
        image: ImageView,
        row: Int,
        rows: Int,
        column: Int,
        columns: Int,
        container: ViewGroup
    )

    override fun draw(
        icon: DrawIcon,
        displayNotchArea: Boolean,
        deviceCutout: DeviceCutout,
        deviceSize: DeviceScreenSize,
        rootViewWindowInsets: WindowInsets?
    ) {
        val (deviceWidth, deviceHeight) = deviceSize

        var iconWidth = icon.drawableWidth.dpiToPx(context)
        var iconHeight = icon.drawableHeight.dpiToPx(context)

        val columns = deviceWidth / iconWidth
        val rows = deviceHeight / iconHeight

        val listWidth = columns * iconWidth
        val listHeight = rows * iconHeight

        if (listWidth < deviceWidth) {
            val space = deviceWidth - listWidth
            val extra = space / columns
            iconWidth += extra
        }

        if (listHeight < deviceHeight) {
            val space = deviceHeight - listHeight
            val extra = space / rows
            iconHeight += extra
        }

        // Draw specified patterns
        for (row: Int in 0 until rows) {
            for (col: Int in 0 until columns) {
                if (displayNotchArea && deviceCutout.isNotchFoundAt(
                        rootViewWindowInsets = rootViewWindowInsets,
                        row = row,
                        col = col,
                        iconWidth = iconWidth,
                        iconHeight = iconHeight
                    )
                ) {
                    continue
                }

                val image = toImage(
                    icon = icon.drawableIcon,
                    widthPx = iconWidth,
                    heightPx = iconHeight,
                    row = row,
                    col = col
                )

                // Draw required patterns
                drawPattern(
                    image = image,
                    row = row,
                    column = col,
                    rows = rows,
                    columns = columns,
                    container = icon.container
                )
            }
        }
    }

    override fun smash(x: Int, y: Int, viewGroup: ViewGroup, onCompletelySmashed: () -> Unit) {
        for (index in 0 until viewGroup.childCount) {
            val child: View? = viewGroup.getChildAt(index)

            if (child != null && child.isVisible) {
                val bounds = Rect()
                child.getHitRect(bounds)

                val found = bounds.contains(x, y)
                val available = child.x.toInt() == x && child.y.toInt() == y

                if (available || found) {
                    viewGroup.removeViewAt(index)
                    break
                }
            }
        }

        if (viewGroup.childCount <= 0) {
            onCompletelySmashed.invoke()
        }
    }

    private fun toImage(icon: Int, widthPx: Int, heightPx: Int, row: Int, col: Int): ImageView {
        return ImageView(context).apply {
            setImageResource(icon)

            layoutParams = FrameLayout.LayoutParams(widthPx, heightPx).apply {
                leftMargin = col * widthPx
                topMargin = row * heightPx
            }
        }
    }
}