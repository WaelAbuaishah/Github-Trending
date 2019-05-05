package matic.apps.maticgithubreposampleandroid.api

import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ResponseInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


 class RetrofitNetworking {

    fun <T> call(responseInterface: ResponseInterface<T>, call: Call<T>) {
        call.clone().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null || response.code() == 204) {
                        // this is the case of empty response
                        println("RetrofitNetworking.onResponse Empty Response.")
                        responseInterface.onSuccess(ApiEmptyResponse())
                    } else {
                        println("RetrofitNetworking.onResponse Success Response, no body")
                        // RetrofitNetworking.onResponse Empty Response. Body RepoSearchResponse(total=158, items=[])
                        responseInterface?.onSuccess(
                            ApiSuccessResponse(
                                body = body,
                                linkHeader = response.headers()?.get("link")
                            )
                        )
                    }
                } else {
                    val msg = response.errorBody()?.string()
                    val errorMsg = if (msg.isNullOrEmpty()) {
                        response.message()
                    } else {
                        msg
                    }

                    if (errorMsg != null) {
                        println("RetrofitNetworking.onResponse with error $errorMsg")
                    }
                    responseInterface?.onError(ApiErrorResponse(errorMsg ?: "unknown error"))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (t.message != null) {
                    println("RetrofitNetworking.onFailure : " + t.message)
                }
                responseInterface.onGeneralError(t)
            }
        })
    }
}