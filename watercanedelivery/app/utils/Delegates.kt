package com.kotlin.mvvm.sample.utils

import kotlinx.coroutines.*
/**
 * Created by Karthikeyan on 27-01-2021.
 */
fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}