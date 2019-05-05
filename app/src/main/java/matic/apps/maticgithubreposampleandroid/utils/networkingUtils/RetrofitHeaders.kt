package matic.apps.maticgithubreposampleandroid.utils.networkingUtils

import okhttp3.Headers

abstract class RetrofitHeaders {

    private var requestHeaders: Headers? = null

    init {
        requestHeaders = buildRetrofitRequestHeaders()
    }

    fun addHeader(key: String, value: String) {
        requestHeaders = requestHeaders!!.newBuilder()
                .removeAll(key)
                .add(key, value)
                .build()
    }

    fun removeHeader(key: String) {
        requestHeaders = requestHeaders!!.newBuilder()
                .removeAll(key)
                .build()
    }

    fun provideRetrofitHeaders(): Headers? {
        return requestHeaders
    }


    abstract fun buildRetrofitRequestHeaders(): Headers

}
