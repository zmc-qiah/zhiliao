package org.jxxy.debug.theme.viewHolder

import android.util.Log
import android.view.View
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.jxxy.debug.corekit.util.toast

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
class MySlideLayoutManager(private val mRecyclerView: RecyclerView, private val mItemTouchHelper: ItemTouchHelper) :
    RecyclerView.LayoutManager() {
    val TAG ="MySlideLayoutManager"
    private val mOnTouchListener =
        View.OnTouchListener { v, event ->
            val childViewHolder = this@MySlideLayoutManager.mRecyclerView.getChildViewHolder(v)
            if (MotionEventCompat.getActionMasked(event) == 0) {
                mItemTouchHelper.startSwipe(childViewHolder)
            }
            false
        }

    private fun <T> checkIsNull(t: T?): T {
        return t ?: throw NullPointerException()
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(-2, -2)
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = this.itemCount
        var position: Int
        var view: View
        var widthSpace: Int
        var heightSpace: Int
        if (itemCount > 3) {
            position = 3
            while (position >= 0) {
                view = recycler.getViewForPosition(position)
                this.addView(view)
                measureChildWithMargins(view, 0, 0)
                widthSpace = this.width - getDecoratedMeasuredWidth(view)
                heightSpace = this.height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view,
                    widthSpace / 2,
                    heightSpace / 5,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 5 + getDecoratedMeasuredHeight(view)
                )
                if (position == 3) {
                    view.scaleX = 1.0f - (position - 1).toFloat() * 0.1f
                    view.scaleY = 1.0f - (position - 1).toFloat() * 0.1f
                    view.translationY = ((position - 1) * view.measuredHeight / 14).toFloat()
                } else if (position > 0) {
                    view.scaleX = 1.0f - position.toFloat() * 0.1f
                    view.scaleY = 1.0f - position.toFloat() * 0.1f
                    view.translationY = (position * view.measuredHeight / 14).toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
                --position
            }
        } else {
            position = itemCount - 1
            while (position >= 0) {
                view = recycler.getViewForPosition(position)
                this.addView(view)
                measureChildWithMargins(view, 0, 0)
                widthSpace = this.width - getDecoratedMeasuredWidth(view)
                heightSpace = this.height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view,
                    widthSpace / 2,
                    heightSpace / 5,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 5 + getDecoratedMeasuredHeight(view)
                )
                if (position > 0) {
                    view.scaleX = 1.0f - position.toFloat() * 0.1f
                    view.scaleY = 1.0f - position.toFloat() * 0.1f
                    view.translationY = (position * view.measuredHeight / 14).toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
                --position
            }
        }
    }
}