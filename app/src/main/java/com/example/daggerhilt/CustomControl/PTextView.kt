package com.example.daggerhilt.CustomControl


import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.daggerhilt.R
import com.google.android.material.textview.MaterialTextView

class PTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textStyle
) : MaterialTextView(context!!, attrs, defStyleAttr) {

    init {
        context?.let {
            // Get the attribute values for textStyle and textColor
            val textStyleArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.text_style, 0, 0)
            val textColorArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.text_color_style, 0, 0)

            val textStyle =
                textStyleArray.getInt(R.styleable.text_style_textStyle, FontsUtils.NORMAL)

            // Set the font style
            typeface = FontsUtils.getTypeface(textStyle, it)

            // Check if custom textColor attribute is specified
            if (textColorArray.hasValue(R.styleable.text_color_style_textColor)) {
                // Apply the custom text color
                setTextColor(
                    textColorArray.getColor(
                        R.styleable.text_color_style_textColor,
                        ContextCompat.getColor(it, android.R.color.black)
                    )
                )
            } else {
                // Set a default text color if custom color is not specified
                setDefaultTextColor()
            }

            textStyleArray.recycle()
            textColorArray.recycle()
        }
    }

    private fun setDefaultTextColor() {
        // Set default text color here (e.g., black)
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
    }
}
