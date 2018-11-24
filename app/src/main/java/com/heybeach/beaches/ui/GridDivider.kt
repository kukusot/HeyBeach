package com.heybeach.beaches.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.R

class GridDivider(context: Context) : RecyclerView.ItemDecoration() {

    private val itemOffSet = context.resources.getDimensionPixelSize(R.dimen.grid_divider_size)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            left = itemOffSet
            right = itemOffSet
            top = itemOffSet
            bottom = itemOffSet
        }
    }

}
