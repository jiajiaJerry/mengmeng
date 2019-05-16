package com.jiajia.coco.mengmeng.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jiajia.coco.mengmeng.R
import org.jetbrains.anko.sp

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description
 */
class SlideBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var sectionHeight = 0f
    private var paint = Paint()
    private var textBaseline: Float = 0.0f

    var onSectionChangeListener: OnSectionChangeListener? = null

    companion object {
        private val SECTIONS = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )
    }

    init {
        paint.apply {
            color = resources.getColor(R.color.qq_section_text_color)
            textSize = sp(12).toFloat()
            //对其居中
            textAlign = Paint.Align.CENTER
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //计算每个字符分配的高度
        sectionHeight = h * 1.0f / SECTIONS.size

        val fontMetrics = paint.fontMetrics
        //计算绘制文本的高度
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        //计算基准线
        textBaseline = sectionHeight / 2 + (textHeight / 2 - fontMetrics.descent)

    }

    override fun onDraw(canvas: Canvas) {
        val x = width * 1.0f / 2
        var baseline = textBaseline
        SECTIONS.forEach {
            canvas.drawText(it, x, baseline, paint)
            //更新y,绘制下一个字符
            baseline += sectionHeight
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                setBackgroundColor(R.drawable.bg_slide_bar)
                //找到点击的字幕
                val index = getTouchIndx(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_MOVE -> {
                //找到点击的字幕
                val index = getTouchIndx(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.TRANSPARENT)
                onSectionChangeListener?.onSildFinish()
            }
        }
        return true
    }

    private fun getTouchIndx(event: MotionEvent): Int {
        var index = (event.y / sectionHeight).toInt()
        if (index < 0)
            index = 0
        else if (index >= SECTIONS.size)
            index = SECTIONS.size - 1
        return index
    }

    interface OnSectionChangeListener {
        fun onSectionChange(firstLetter: String)
        fun onSildFinish()//滑动结束回调
    }
}