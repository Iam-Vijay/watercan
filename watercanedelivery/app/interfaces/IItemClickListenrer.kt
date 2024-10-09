package com.watercanedelivery.app.interfaces

import android.view.View

/**
 * Created by Karthikeyan on 06/03/2021.
 */
interface IItemClickListenrer {

    fun itemClick(pos: Int, view: View)
    fun cusItemClick(id: String,view: View) {

    }
}