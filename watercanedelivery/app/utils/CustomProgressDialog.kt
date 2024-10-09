package com.watercanedelivery.app.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.watercanedelivery.app.R
import retrofit2.Call

class CustomProgressDialog : Dialog {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, theme: Int) : super(context!!, theme) {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val imageView =
            findViewById<View>(R.id.spinnerImageView) as ImageView
        val spinner = imageView.background as AnimationDrawable
        spinner.start()
    }

    companion object {

        fun show(
            context: Context,
            indeterminate: Boolean,
            cancelable: Boolean
        ): CustomProgressDialog {
            val dialog =
                CustomProgressDialog(context, R.style.ProgressDialogTheme)
            // dialog.getWindow().setWindowAnimations(R.style.zoom);
            dialog.setTitle("")
            dialog.setContentView(R.layout.progress_dialog_layout)


            dialog.setCancelable(cancelable)
            dialog.window!!.attributes.gravity = Gravity.CENTER
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
            dialog.window!!.setWindowAnimations(R.style.dialog_zoom)
            dialog.show()
            return dialog
        }

    }
}
