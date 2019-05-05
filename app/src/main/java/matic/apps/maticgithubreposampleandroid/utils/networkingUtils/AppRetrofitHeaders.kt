package matic.apps.maticgithubreposampleandroid.utils.networkingUtils

import okhttp3.Headers

class AppRetrofitHeaders : RetrofitHeaders() {


    override fun buildRetrofitRequestHeaders(): Headers {
        val headersBuilder = Headers.Builder()

        // here to add headers for all request, like content type and accept and others
        return headersBuilder.build()
    }
}
