package com.watercanedelivery.app.utils.customView

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

/**
 * Created by karthikeyan on 02/02/2021.
 */

open class CustomBlackRomanTextview : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        setFont()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setFont()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setFont()
    }

    private fun setFont() {
        val font =
            Typeface.createFromAsset(context.assets, "fonts/Avenir-LT-Std-55-Roman_5173.ttf")
        setTypeface(font, Typeface.NORMAL)
    }


}
