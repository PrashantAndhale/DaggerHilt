package com.example.daggerhilt.CustomControl


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.daggerhilt.R

class PTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textStyle
) : AppCompatTextView(context!!, attrs, defStyleAttr) {

    init {
        context?.let {
            // Get the attribute value for textStyle
            val attributeArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.text_style, 0, 0)
            val textStyle =
                attributeArray.getInt(R.styleable.text_style_textStyle, FontsUtils.NORMAL)
            attributeArray.recycle()

            // Set the font style
            typeface = FontsUtils.getTypeface(textStyle, it)
        }
    }
}