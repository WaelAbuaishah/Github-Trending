package matic.apps.maticgithubreposampleandroid.utils.networkingUtils

import matic.apps.maticgithubreposampleandroid.api.ApiResponse

interface ResponseInterface<T> {

    fun onSuccess(response: ApiResponse<T>?)

    fun onError(response: ApiResponse<T>?)

    fun onGeneralError(error: Throwable)

    fun onOtherError(error: Throwable)
}