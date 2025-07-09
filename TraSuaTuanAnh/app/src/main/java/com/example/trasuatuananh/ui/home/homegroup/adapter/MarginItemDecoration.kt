package com.example.trasuatuananh.ui.home.homegroup.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val context: Context,
    private val firstItemBottomMargin: Int,
    private val otherItemsMargin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 1) {
            outRect.bottom = context.resources.getDimensionPixelOffset(firstItemBottomMargin)
        } else if (position != 0) {
            outRect.top = context.resources.getDimensionPixelOffset(otherItemsMargin)
            outRect.bottom = context.resources.getDimensionPixelOffset(otherItemsMargin)
        }
    }
}