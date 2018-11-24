package com.heybeach.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class RatioImageView(ctx: Context, attributeSet: AttributeSet) : ImageView(ctx, attributeSet) {

    private var _measuredWidth: Int = 0
    var ratio: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        _measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val rationHeight = (_measuredWidth * ratio).toInt()
        setMeasuredDimension(_measuredWidth, rationHeight)
    }

}