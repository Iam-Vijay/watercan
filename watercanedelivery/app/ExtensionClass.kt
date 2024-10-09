package com.watercanedelivery.app

/**
 * Created by Karthikeyan on 29/04/2021.
 */
class ExtensionClass(val name: String) {
    fun main() {
        "vijay is a good boy".convertSpaceUnderScore()
    }
}

fun String.convertSpaceUnderScore(): String {
    return this.replace(" ", "-")
}


