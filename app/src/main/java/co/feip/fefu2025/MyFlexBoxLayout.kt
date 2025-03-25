package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class MyFlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val lineSpacing = 16
    private val itemSpacing = 16
    private val paddingHorizontal = 16
    private val paddingVertical = 20

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingHorizontal * 2
        var totalWidth = 0
        var totalHeight = 0
        var lineWidth = 0
        var lineHeight = 0

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (lineWidth + childWidth + (if (lineWidth > 0) itemSpacing else 0) > width) {
                totalWidth = max(totalWidth, lineWidth)
                totalHeight += lineHeight + lineSpacing
                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineWidth += childWidth + (if (lineWidth > 0) itemSpacing else 0)
                lineHeight = max(lineHeight, childHeight)
            }
        }

        totalWidth = max(totalWidth, lineWidth)
        totalHeight += lineHeight + paddingVertical * 2

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(totalWidth + paddingHorizontal * 2, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var lineWidth = 0
        var lineHeight = 0
        var currentTop = paddingVertical
        var currentLeft = paddingHorizontal

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight

                if (lineWidth + childWidth + (if (lineWidth > 0) itemSpacing else 0) > width - paddingHorizontal * 2) {
                    currentTop += lineHeight + lineSpacing
                    currentLeft = paddingHorizontal
                    lineWidth = 0
                    lineHeight = 0
                }

                child.layout(currentLeft, currentTop, currentLeft + childWidth, currentTop + childHeight)

                lineWidth += childWidth + (if (lineWidth > 0) itemSpacing else 0)
                lineHeight = max(lineHeight, childHeight)
                currentLeft += childWidth + itemSpacing
            }
        }
    }
}