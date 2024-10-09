package com.com.watercanedelivery.utils

import com.watercanedelivery.utils.APIStatus
import retrofit2.Response

/**
 * Created by Karthikeyan on 27-01-2021.
 */


class APIResponse<T>{

     private var status: APIStatus? = null

     private var data: T? = null

    private var listData: List<T>? = null

    private var error: Throwable? = null
    private var response: Response<T>? = null



    fun loading(): APIResponse<T> {
        this.status = APIStatus.LOADING
        this.data = null
        listData = null
        error = null
        return this
    }

    fun success(data: T): APIResponse<T> {
        this.status = APIStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun success(data: List<T>): APIResponse<T> {
        this.status = APIStatus.SUCCESS
        listData = data
        error = null
        return this
    }

    fun error(error: Throwable): APIResponse<T> {
        this.status = APIStatus.ERROR
        this.data = null
        listData = null
        this.error = error
        return this
    }

    fun failure(response: Response<T>): APIResponse<T>{
        this.status = APIStatus.FAILURE
        this.data = null
        listData = null
        this.response = response
        return this
    }


    fun getStatus(): APIStatus {
        return this.status!!
    }

    fun getData(): T? {
        return data
    }

    fun getListData(): List<T>? {
        return listData
    }

    fun getError(): Throwable? {
        return error
    }

    fun getFailureResponse(): Response<T>? {
        return response
    }
}