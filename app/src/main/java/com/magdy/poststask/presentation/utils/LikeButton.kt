package com.magdy.poststask.presentation.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.magdy.poststask.R

class LikeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isLiked = false
    private var heartIcon: Drawable? = null
    private var iconSize: Int = 0
    private var likedColor: Int = 0
    private var unlikedColor: Int = 0
    private var scaleAnimation: ScaleAnimation? = null

    init {
        paint.style = Paint.Style.FILL

        heartIcon = ContextCompat.getDrawable(context, R.drawable.ic_like)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeButton, defStyleAttr, 0)
        iconSize = typedArray.getDimensionPixelSize(R.styleable.LikeButton_iconSize, 0)
        likedColor = typedArray.getColor(R.styleable.LikeButton_likedColor, ContextCompat.getColor(context, android.R.color.holo_red_dark))
        unlikedColor = typedArray.getColor(R.styleable.LikeButton_unlikedColor, ContextCompat.getColor(context, android.R.color.darker_gray))
        typedArray.recycle()

        updateIconColor()
        setOnClickListener {
            isLiked = !isLiked
            updateIconColor()
            startAnimation()
            requestLayout()
        }
    }

    private fun updateIconColor() {
        val color = if (isLiked) likedColor else unlikedColor
        val colorFilter: ColorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        heartIcon?.colorFilter = colorFilter
    }

    private fun startAnimation() {
        scaleAnimation = if (isLiked) {
            ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        } else {
            ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        }

        scaleAnimation?.duration = 300
        startAnimation(scaleAnimation)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

         val calculatedIconSize = if (iconSize > 0) iconSize else heartIcon?.intrinsicWidth ?: 0
        setMeasuredDimension(calculatedIconSize, calculatedIconSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the heart icon centered within the widget
        val iconSize = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom)
        val iconLeft = (width - iconSize) / 2
        val iconTop = (height - iconSize) / 2

        heartIcon?.setBounds(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize)
        heartIcon?.draw(canvas)
    }
}


