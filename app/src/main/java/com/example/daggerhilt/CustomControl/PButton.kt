package com.example.daggerhilt.CustomControl

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.example.daggerhilt.R
import com.google.android.material.button.MaterialButton

@SuppressLint("CustomViewStyleable")
class PButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton // Use the correct style resource ID
) : MaterialButton(
    ContextThemeWrapper(context, defStyleAttr), attrs, defStyleAttr
){
    init {
        context.let { ctx ->
            val textStyleArray =
                ctx.obtainStyledAttributes(attrs, R.styleable.text_style, 0, 0)
            val textColorArray =
                ctx.obtainStyledAttributes(attrs, R.styleable.text_color_style, 0, 0)
            val textSizeArray =
                ctx.obtainStyledAttributes(attrs, R.styleable.text_size, 0, 0)

            // Set the font style
            typeface = FontsUtils.getTypeface(
                textStyleArray.getInt(R.styleable.text_style_textStyle, FontsUtils.NORMAL),
                ctx
            )

            // Apply custom text color
            setTextColor(
                textColorArray.getColor(
                    R.styleable.text_color_style_textColor,
                    ContextCompat.getColor(ctx, android.R.color.white)
                )
            )

            // Apply custom text size
            if (textSizeArray.hasValue(R.styleable.text_size_textSize)) {
                val defaultTextSize = resources.getDimension(R.dimen.text_size_medium)
                val customTextSize =
                    textSizeArray.getDimension(R.styleable.text_size_textSize, defaultTextSize)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, customTextSize)
            } else {
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.text_size_medium)
                )
            }

            textSizeArray.recycle()
            textStyleArray.recycle()
            textColorArray.recycle()
        }
    }
}
