package com.kotlin.mvvm.sample.utils

import java.io.IOException
/**
 * Created by Karthikeyan on 27-01-2021.
 */
class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)