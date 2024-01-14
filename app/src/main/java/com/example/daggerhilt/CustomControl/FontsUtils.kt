package com.example.daggerhilt.CustomControl

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.example.daggerhilt.R

object FontsUtils {
    const val NORMAL = 0
    const val BOLD = 1
    const val ITALIC = 2
    const val BOLD_ITALIC = 3
    const val HEAVY = 4
    const val THIN = 5

    private val fontCache = HashMap<Int, Typeface?>()

    private val fontFileMap = mapOf(
        HEAVY to "avenir_heavy.otf",
        BOLD to "avenir_heavy.otf",
        BOLD_ITALIC to "avenir_book_normal.otf",
        ITALIC to "avenir_book_normal.otf",
        THIN to "avenir_medium.otf",
        NORMAL to "avenir_book_normal.otf"
    )

    fun setFont(
        element: TextView,
        context: Context,
        attrs: AttributeSet?
    ) {
        val attributeArray =
            context.obtainStyledAttributes(attrs, R.styleable.text_style)
        val textStyle =
            attributeArray.getInt(R.styleable.text_style_textStyle, NORMAL)
        element.setTypeface(getTypeface(textStyle, context))
        attributeArray.recycle()
    }

    fun getTypeface(fontStyle: Int, context: Context): Typeface? {
        var typeface = fontCache[fontStyle]
        if (typeface == null) {
            typeface = try {
                getFont(fontStyle, context)
            } catch (e: Exception) {
                return null
            }
            fontCache[fontStyle] = typeface
        }
        return typeface
    }

    private fun getFont(fontForStyle: Int, context: Context): Typeface {
        val fontFileName = fontFileMap[fontForStyle] ?: "avenir_book_normal.otf"
        return Typeface.createFromAsset(context.assets, fontFileName)
    }
}

