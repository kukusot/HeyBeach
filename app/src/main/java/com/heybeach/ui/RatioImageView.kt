package com.heybeach.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class RatioImageView(ctx: Context, attributeSet: AttributeSet) : ImageView(ctx, attributeSet) {

    fun setRatio(width: Int, height: Int) {
        layoutParams.height = this.width * (height / width)
        invalidate()
    }

}