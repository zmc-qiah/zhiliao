package org.jxxy.debug.resources.activity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.Nullable
import org.jxxy.debug.common.R
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.resources.bean.TagInfo

class MyTextView @JvmOverloads constructor(
    context: Context,
    @Nullable attributeSet:AttributeSet?,
    defStyle:Int = 0
):androidx.appcompat.widget.AppCompatTextView(context, attributeSet, defStyle) {
    private  val mRect: Rect
    private  val mPaint: Paint
    private val mColor = ResourceUtil.getColor(R.color.primary_200)
    private var density :Float = 1.dp<Float>()
    private var mStrokeWidth = 0f
    private var canvas :Canvas? = null
    var tagList :List<TagInfo> ?= null
    init {
        mStrokeWidth = density;
        mRect =  Rect();
        mPaint =  Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
    }
    private val TAG ="MyTextView"
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        tagList?.let {
            it.forEach {
                drawUnderLine(it.start,it.end,canvas)
            }
        }
    }
    fun drawUnderLine(start2: Int, end2: Int,canvas: Canvas?){
        val baseOffset =  if (tag is Int) tag as Int else 0
        var start = start2 - baseOffset
        var end = end2 - baseOffset
        val totalLineCount = lineCount -1
        Log.d(TAG, "drawUnderLine: ${totalLineCount}")
        val layout = this.layout
        var isEnd = false
        for ( i in 0.. totalLineCount){
            val start1 = layout.getLineStart(i)
            val end1 = layout.getLineEnd(i)
            var startOffset = 0
            var endOffset = 0
            if (start<=end1&&start>=start1){
                startOffset = start
                if (end<=end1){
                    endOffset = end
                    isEnd = true
                }else {
                    endOffset = end1
                    start -=(endOffset - startOffset)
                }
                val baseLine = getLineBounds(i,mRect)
                val x_start = layout.getPrimaryHorizontal(startOffset)
                val x_diff  = layout.getPrimaryHorizontal(startOffset+1)-x_start
                val x_stop  = layout.getPrimaryHorizontal(endOffset+1)+x_diff
                canvas?.drawLine(x_start,baseLine+mStrokeWidth,x_stop,baseLine+mStrokeWidth,mPaint)
                if (isEnd) break
            }
        }
    }
}
