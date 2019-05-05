package matic.apps.maticgithubreposampleandroid.utils.networkingUtils

import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.NetworkingConstants.ADVANCED_HEADER_KEY
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.NetworkingConstants.ADVANCED_HEADER_VALUE
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.NetworkingConstants.PROTOCOL_SSL
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.NetworkingConstants.TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class InitApi {
    private var requestHeaders: RetrofitHeaders? = null


    companion object {
        private var instance: InitApi? = null
        var retrofit: Retrofit? = null

        fun getInstance(): InitApi {
            if (instance == null) {
                instance = InitApi()
            }
            return instance as InitApi
        }

        fun getRetrofitInstance(): Retrofit {
            return retrofit!!
        }

        fun initialize() {
            val instance = getInstance()
            instance.createRetrofit()
        }
    }

    fun getRetrofitInstance(): Retrofit {
        instance!!.createRetrofit()
        return retrofit!!
    }

    private fun createRetrofit() {
        val x509TrustManager = provideX509TrustManager()
        val sslContext = provideSSLContext(x509TrustManager)
        val okHttpClient = provideOkHttpClient(sslContext, x509TrustManager)
        retrofit = provideRetrofit(NetworkingConstants.BASE_URL, okHttpClient, GsonConverterFactory.create())
    }

    private fun provideSSLContext(x509TrustManager: X509TrustManager): SSLContext {
        try {
            val trustManagers = arrayOf<TrustManager>(x509TrustManager)
            val sslContext = SSLContext.getInstance(PROTOCOL_SSL)
            sslContext.init(null, trustManagers, java.security.SecureRandom())
            return sslContext
        } catch (e: Exception) {
            throw RuntimeException("Could not provide SSL context!", e)
        }
    }

    private fun provideX509TrustManager(): X509TrustManager {

        return object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate?> { return arrayOfNulls(0) }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        }
    }

    private fun addHeader(key: String, value: String) {
        if (requestHeaders == null) {
            return
        }
        requestHeaders!!.addHeader(key, value)
    }

    private fun removeHeader(key: String) {
        if (requestHeaders == null) {
            return
        }
        requestHeaders!!.removeHeader(key)
    }


    private fun provideRetrofit(
        endpoint: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun provideOkHttpClient(sslContext: SSLContext, x509TrustManager: X509TrustManager): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()

                // Add headers if any
                val headers = requestHeaders
                if (headers != null) {
                    requestBuilder.headers(headers.provideRetrofitHeaders()!!)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    fun setRequestHeaders(headers: RetrofitHeaders) {
        this.requestHeaders = headers
    }

    fun addAdvanceHeader() {
        addHeader(ADVANCED_HEADER_KEY, ADVANCED_HEADER_VALUE)
    }

    fun addContentTypeHeader() {
        addHeader("Accept", "application/hal+json")
        addHeader("Content-Type", "application/json")
    }

    fun addContentTypeCustom() {
        addHeader("Content-Type", "application/vnd.customer.v2+json")
    }

    fun addAdvance() {
        addHeader("Content-Type", "application/json")
        addHeader("Accept-Response", "Advanced")
    }

    fun removeContentTypeHeader() {
        removeHeader("Content-Type")
    }

    fun removeAdvanceHeader() {
        removeHeader(ADVANCED_HEADER_KEY)
    }

}

