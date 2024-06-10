package com.debug.myapplication.Selectbale

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Spannable
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.ColorInt
import org.jxxy.debug.common.databinding.LayoutOperateWindowsBinding
import org.jxxy.debug.common.util.dp2px
import org.jxxy.debug.common.util.getHysteresisOffset
import org.jxxy.debug.common.util.getPreciseOffset
import org.jxxy.debug.common.util.getScreenWidth
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.corekit.util.singleClick


// https://blog.csdn.net/plokmju88/article/details/103355076
// https://github.com/laobie/SelectableTextHelper/
class SelectableTextHelper(builder: Builder) {
    private var mStartHandle: CursorHandle? = null
    private var mEndHandle: CursorHandle? = null
    private var mOperateWindow: OperateWindow? = null
    private val mSelectionInfo: SelectionInfo = SelectionInfo()
    private var mSelectListener: OnSelectListener? = null
    private val mContext: Context
    private val mTextView: TextView
    private var mSpannable: Spannable? = null
    private var mTouchX = 0
    private var mTouchY = 0
    private val mSelectedColor: Int
    private val mCursorHandleColor: Int
    private val mCursorHandleSize: Int
    private var mSpan: BackgroundColorSpan? = null
    private var isHideWhenScroll = false
    private var isHide = true
    private var mOnPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null
    var mOnScrollChangedListener: OnScrollChangedListener? = null
    private fun init() {
        mTextView.setText(mTextView.text, TextView.BufferType.SPANNABLE)
        mTextView.setOnLongClickListener {
            showSelectView(mTouchX, mTouchY)
            true
        }
        mTextView.setOnTouchListener { v, event ->
            mTouchX = event.x.toInt()
            mTouchY = event.y.toInt()
            false
        }

        mTextView.setOnClickListener {
            val startOffset: Int = getPreciseOffset(mTextView, mTouchX, mTouchY)
            val endOffset = startOffset + DEFAULT_SELECTION_LENGTH
            val i :Int
            if (it.tag is Int){
                i = it.tag as Int
            }else{
                i = 0
            }
            mSelectListener?.singleClick(i,startOffset,endOffset)
            resetSelectionInfo()
            hideSelectView()
        }
        mTextView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {}
            override fun onViewDetachedFromWindow(v: View) {
                destroy()
            }
        })
        mOnPreDrawListener = ViewTreeObserver.OnPreDrawListener {
            if (isHideWhenScroll) {
                isHideWhenScroll = false
                postShowSelectView(DEFAULT_SHOW_DURATION)
            }
            true
        }
        mTextView.viewTreeObserver.addOnPreDrawListener(mOnPreDrawListener)
        mOnScrollChangedListener = OnScrollChangedListener {
            if (!isHideWhenScroll && !isHide) {
                isHideWhenScroll = true
                if (mOperateWindow != null) {
                    mOperateWindow!!.dismiss()
                }
                if (mStartHandle != null) {
                    mStartHandle!!.dismiss()
                }
                if (mEndHandle != null) {
                    mEndHandle!!.dismiss()
                }
            }
        }
        mTextView.viewTreeObserver.addOnScrollChangedListener(mOnScrollChangedListener)
        mOperateWindow = OperateWindow(mContext)
    }

    private fun postShowSelectView(duration: Int) {
        mTextView.removeCallbacks(mShowSelectViewRunnable)
        if (duration <= 0) {
            mShowSelectViewRunnable.run()
        } else {
            mTextView.postDelayed(mShowSelectViewRunnable, duration.toLong())
        }
    }

    private val mShowSelectViewRunnable = Runnable {
        if (isHide) {
            return@Runnable
        }
        if (mOperateWindow != null) {
            mOperateWindow!!.show()
        }
        if (mStartHandle != null) {
            showCursorHandle(mStartHandle!!)
        }
        if (mEndHandle != null) {
            showCursorHandle(mEndHandle!!)
        }
    }

    init {
        mTextView = builder.mTextView
        mContext = mTextView.context
        mSelectedColor = builder.mSelectedColor
        mCursorHandleColor = builder.mCursorHandleColor
        mCursorHandleSize = dp2px(mContext, builder.mCursorHandleSizeInDp)
        mSelectListener = builder.listener
        init()
    }

    private fun hideSelectView() {
        isHide = true
        if (mStartHandle != null) {
            mStartHandle!!.dismiss()
        }
        if (mEndHandle != null) {
            mEndHandle!!.dismiss()
        }
        if (mOperateWindow != null) {
            mOperateWindow!!.dismiss()
        }
    }

    private fun resetSelectionInfo() {
        mSelectionInfo.selectionContent = null
        if (mSpannable != null && mSpan != null) {
            mSpannable!!.removeSpan(mSpan)
            mSpan = null
        }
    }

    private fun showSelectView(x: Int, y: Int) {
        hideSelectView()
        resetSelectionInfo()
        isHide = false
        if (mStartHandle == null) {
            mStartHandle = CursorHandle(true)
        }
        if (mEndHandle == null) {
            mEndHandle = CursorHandle(false)
        }
        val startOffset: Int = getPreciseOffset(mTextView, x, y)
        val endOffset = startOffset + DEFAULT_SELECTION_LENGTH
        if (mTextView.text is Spannable) {
            mSpannable = mTextView.text as Spannable
        }
        if (mSpannable == null || startOffset >= mTextView.text.length) {
            return
        }
        selectText(startOffset, endOffset)
        showCursorHandle(mStartHandle!!)
        showCursorHandle(mEndHandle!!)
        mOperateWindow!!.show()
    }

    private fun showCursorHandle(cursorHandle: CursorHandle) {
        val layout = mTextView.layout
        val offset: Int =
            if (cursorHandle.isLeft) mSelectionInfo.start else mSelectionInfo.end
        cursorHandle.show(
            layout.getPrimaryHorizontal(offset).toInt(),
            layout.getLineBottom(layout.getLineForOffset(offset))
        )
    }

    private fun selectText(startPos: Int, endPos: Int) {
        if (startPos != -1) {
            mSelectionInfo.start = startPos
        }
        if (endPos != -1) {
            mSelectionInfo.end = endPos
        }
        if (mSelectionInfo.start > mSelectionInfo.end) {
            val temp: Int = mSelectionInfo.start
            mSelectionInfo.start = mSelectionInfo.end
            mSelectionInfo.end = temp
        }
        if (mSpannable != null) {
            if (mSpan == null) {
                mSpan = BackgroundColorSpan(mSelectedColor)
            }
            mSelectionInfo.selectionContent = mSpannable!!.subSequence(
                mSelectionInfo.start,
                mSelectionInfo.end
            ).toString()
            mSpannable!!.setSpan(
                mSpan,
                mSelectionInfo.start,
                mSelectionInfo.end,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            if (mSelectListener != null) {
                mSelectListener!!.onTextSelected(mSelectionInfo.selectionContent)
            }
        }
    }

    fun setSelectListener(selectListener: OnSelectListener?) {
        mSelectListener = selectListener
    }

    fun destroy() {
        mTextView.viewTreeObserver.removeOnScrollChangedListener(mOnScrollChangedListener)
        mTextView.viewTreeObserver.removeOnPreDrawListener(mOnPreDrawListener)
        resetSelectionInfo()
        hideSelectView()
        mStartHandle = null
        mEndHandle = null
        mOperateWindow = null
    }

    fun dismiss() {
        resetSelectionInfo()
        hideSelectView()
    }

    /**
     * Operate windows : copy, select all
     */
    private inner class OperateWindow(context: Context?) {
        private val mWindow: PopupWindow
        private val mTempCoors = IntArray(2)
        private val mWidth: Int
        private val mHeight: Int
        val binding:LayoutOperateWindowsBinding

        init {
            binding = LayoutOperateWindowsBinding.inflate(LayoutInflater.from(context))
            val contentView: View =binding.root
            contentView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            mWidth = contentView.measuredWidth
            mHeight = contentView.measuredHeight
            mWindow = PopupWindow(
                contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                false
            )
            mWindow.isClippingEnabled = false
            binding.apply {
                copyIcon.singleClick {
                    mSelectListener?.copyText(mSelectionInfo.selectionContent)
                    resetSelectionInfo()
                    hideSelectView()
                }
                allIcon.singleClick {
                    resetSelectionInfo()
                    hideSelectView()
                    selectText(0, mTextView.text.length)
                    showCursorHandle(mStartHandle!!)
                    showCursorHandle(mEndHandle!!)
                    mOperateWindow!!.show()
                }
                noteIcon.singleClick {
                    mSelectListener?.takeNote(mSelectionInfo)
                    resetSelectionInfo()
                    hideSelectView()
                }
                markIcon.singleClick {
                    mSelectListener?.markText(mTextView,mSelectionInfo)
                    resetSelectionInfo()
                    hideSelectView()
                }
                canalIcon.singleClick {
                    dismiss()
                    resetSelectionInfo()
                    hideSelectView()
                }
                searchIcon.singleClick {
                    mSelectListener?.textSearch(mSelectionInfo.selectionContent)
                    resetSelectionInfo()
                    hideSelectView()
                }
                copyTextView.singleClick {
                    mSelectListener?.copyText(mSelectionInfo.selectionContent)
                    resetSelectionInfo()
                    hideSelectView()
                }
                allTextView.singleClick {
                    resetSelectionInfo()
                    hideSelectView()
                    selectText(0, mTextView.text.length)
                    showCursorHandle(mStartHandle!!)
                    showCursorHandle(mEndHandle!!)
                    mOperateWindow!!.show()
                }
                noteTextView.singleClick {
                    mSelectListener?.takeNote(mSelectionInfo)
                    resetSelectionInfo()
                    hideSelectView()
                }
                markTextView.singleClick {
                    mSelectListener?.markText(mTextView,mSelectionInfo)
                    resetSelectionInfo()
                    hideSelectView()
                }
                canalTextView.singleClick {
                    dismiss()
                    resetSelectionInfo()
                    hideSelectView()
                }
                searchTextView.singleClick {
                    mSelectListener?.textSearch(mSelectionInfo.selectionContent)
                    resetSelectionInfo()
                    hideSelectView()
                }
            }
        }

        fun show() {
            mTextView.getLocationInWindow(mTempCoors)
            val layout = mTextView.layout
            var posX =
                layout.getPrimaryHorizontal(mSelectionInfo.start).toInt() + mTempCoors[0]
            var posY =
                layout.getLineTop(layout.getLineForOffset(mSelectionInfo.start)) + mTempCoors[1] - mHeight - 16
            val screenWidth = getWidth()
            if (posX + mWidth > screenWidth) {
                posX = screenWidth - mWidth - 16
            }
            if (posX <= 0) {
                posX = 16
            }
            if (posY < 0) {
                posY = 16
            }
            if (posX + mWidth > getScreenWidth()) {
                posX = getScreenWidth() - mWidth - 16
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWindow.elevation = 8f
            }
            mWindow.showAtLocation(mTextView, Gravity.NO_GRAVITY, posX, posY)
        }

        fun dismiss() {
            mWindow.dismiss()
        }

        val isShowing: Boolean
            get() = mWindow.isShowing
    }

    private inner class CursorHandle(var isLeft: Boolean) : View(mContext) {
        private val mPopupWindow: PopupWindow
        private val mPaint: Paint
        private val mCircleRadius = mCursorHandleSize / 2
        private val mWidth = mCircleRadius * 2
        private val mHeight = mCircleRadius * 2
        private val mPadding = 25
        override fun onDraw(canvas: Canvas) { // 绘制光标图案
            canvas.drawCircle(
                (mCircleRadius + mPadding).toFloat(),
                mCircleRadius.toFloat(),
                mCircleRadius.toFloat(),
                mPaint
            )
            if (isLeft) {
                canvas.drawRect(
                    (mCircleRadius + mPadding).toFloat(),
                    0f,
                    (mCircleRadius * 2 + mPadding).toFloat(),
                    mCircleRadius.toFloat(),
                    mPaint
                )
            } else {
                canvas.drawRect(
                    mPadding.toFloat(),
                    0f,
                    (mCircleRadius + mPadding).toFloat(),
                    mCircleRadius.toFloat(),
                    mPaint
                )
            }
        }

        private var mAdjustX = 0
        private var mAdjustY = 0
        private var mBeforeDragStart = 0
        private var mBeforeDragEnd = 0
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mBeforeDragStart = mSelectionInfo.start
                    mBeforeDragEnd = mSelectionInfo.end
                    mAdjustX = event.x.toInt()
                    mAdjustY = event.y.toInt()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mOperateWindow!!.show()
                MotionEvent.ACTION_MOVE -> {
                    mOperateWindow!!.dismiss()
                    val rawX = event.rawX.toInt()
                    val rawY = event.rawY.toInt()
                    update(rawX + mAdjustX - mWidth, rawY + mAdjustY - mHeight)
                }
            }
            return true
        }

        private fun changeDirection() {
            isLeft = !isLeft
            invalidate()
        }

        fun dismiss() {
            mPopupWindow.dismiss()
        }

        private val mTempCoors = IntArray(2)

        init {
            mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPaint.color = mCursorHandleColor
            mPopupWindow = PopupWindow(this)
            mPopupWindow.isClippingEnabled = false
            mPopupWindow.width = mWidth + mPadding * 2
            mPopupWindow.height = mHeight + mPadding / 2
            invalidate()
        }

        fun update(x: Int, y: Int) {
            var y = y
            mTextView.getLocationInWindow(mTempCoors)
            val oldOffset: Int
            oldOffset = if (isLeft) {
                mSelectionInfo.start
            } else {
                mSelectionInfo.end
            }
            y -= mTempCoors[1]
            val offset: Int = getHysteresisOffset(mTextView, x, y, oldOffset)
            if (offset != oldOffset) {
                resetSelectionInfo()
                if (isLeft) {
                    if (offset > mBeforeDragEnd) {
                        val handle = getCursorHandle(false)
                        changeDirection()
                        handle!!.changeDirection()
                        mBeforeDragStart = mBeforeDragEnd
                        selectText(mBeforeDragEnd, offset)
                        handle.updateCursorHandle()
                    } else {
                        selectText(offset, -1)
                    }
                    updateCursorHandle()
                } else {
                    if (offset < mBeforeDragStart) {
                        val handle = getCursorHandle(true)
                        handle!!.changeDirection()
                        changeDirection()
                        mBeforeDragEnd = mBeforeDragStart
                        selectText(offset, mBeforeDragStart)
                        handle.updateCursorHandle()
                    } else {
                        selectText(mBeforeDragStart, offset)
                    }
                    updateCursorHandle()
                }
            }
        }

        private fun updateCursorHandle() {
            mTextView.getLocationInWindow(mTempCoors)
            val layout = mTextView.layout
            if (isLeft) {
                mPopupWindow.update(
                    layout.getPrimaryHorizontal(mSelectionInfo.start)
                        .toInt() - mWidth + extraX,
                    layout.getLineBottom(layout.getLineForOffset(mSelectionInfo.start)) + extraY,
                    -1,
                    -1
                )
            } else {
                mPopupWindow.update(
                    layout.getPrimaryHorizontal(mSelectionInfo.end).toInt() + extraX,
                    layout.getLineBottom(layout.getLineForOffset(mSelectionInfo.end)) + extraY,
                    -1,
                    -1
                )
            }
        }

        fun show(x: Int, y: Int) {
            mTextView.getLocationInWindow(mTempCoors)
            val offset = if (isLeft) mWidth else 0
            mPopupWindow.showAtLocation(
                mTextView,
                Gravity.NO_GRAVITY,
                x - offset + extraX,
                y + extraY
            )
        }

        val extraX: Int
            get() = mTempCoors[0] - mPadding + mTextView.paddingLeft
        val extraY: Int
            get() = mTempCoors[1] + mTextView.paddingTop
    }

    private fun getCursorHandle(isLeft: Boolean): CursorHandle? {
        return if (mStartHandle!!.isLeft == isLeft) {
            mStartHandle
        } else {
            mEndHandle
        }
    }

    interface OnSelectListener {
        fun onTextSelected(content: CharSequence?)
        fun takeNote(selectionInfo: SelectionInfo?)
        fun copyText(content: String?)
        fun textSearch(key: String?)
         fun markText(view:TextView , mSelectionInfo: SelectionInfo)
        fun singleClick(baseOffset:Int,startOffset: Int, endOffset: Int)
    }

    class Builder(val mTextView: TextView) {
        var mCursorHandleColor = -0xec862a
        var mSelectedColor = -0x501e0c
        var mCursorHandleSizeInDp = 24f
        var listener: OnSelectListener ?= null
        fun setCursorHandleColor(@ColorInt cursorHandleColor: Int): Builder {
            mCursorHandleColor = cursorHandleColor
            return this
        }

        fun setCursorHandleSizeInDp(cursorHandleSizeInDp: Float): Builder {
            mCursorHandleSizeInDp = cursorHandleSizeInDp
            return this
        }

        fun setSelectedColor(@ColorInt selectedBgColor: Int): Builder {
            mSelectedColor = selectedBgColor
            return this
        }
        fun setListener(listener: OnSelectListener):Builder{
            this.listener = listener
            return this
        }

        fun build(): SelectableTextHelper {
            return SelectableTextHelper(this)
        }
    }

    companion object {
        private const val DEFAULT_SELECTION_LENGTH = 1
        private const val DEFAULT_SHOW_DURATION = 100
    }
}


