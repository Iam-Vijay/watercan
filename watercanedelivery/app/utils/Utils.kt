package com.watercanedelivery.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.watercanedelivery.app.R
import com.watercanedelivery.app.databinding.PopupDialogOkBinding
import com.watercanedelivery.app.ui.activity.MainActivity

/**
 * Created by Karthikeyan on 14/04/2021.
 */
class Utils {
    companion object {

        private lateinit var alertDialog: AlertDialog
        private lateinit var dialogBuilder: AlertDialog.Builder

        fun loginPopup(
            context: Context, staff_id: String
        ) {
            dialogBuilder = AlertDialog.Builder(context, R.style.ProgressDialogTheme)

            val bindingPopup =
                PopupDialogOkBinding.inflate(LayoutInflater.from(context))
            dialogBuilder.setView(bindingPopup.root)
            alertDialog = dialogBuilder.create()
            alertDialog.setCancelable(false)

            alertDialog.show()
            bindingPopup.txtviewAlert.setText("Malar Mineral")
            bindingPopup.txtviewMessage.setText("Welcome to Malar Mineral This is your user id " + " " + staff_id)
            bindingPopup.btnOk.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                context.startActivity(
                    Intent(
                        context,
                        MainActivity::class.java
                    )
                )

                (context as Activity).finish()
            })

        }
    }

}