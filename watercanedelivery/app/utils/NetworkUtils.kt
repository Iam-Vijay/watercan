package com.watercanedelivery.app.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Karthikeyan on 14/04/2021.
 */
object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false
    }
}