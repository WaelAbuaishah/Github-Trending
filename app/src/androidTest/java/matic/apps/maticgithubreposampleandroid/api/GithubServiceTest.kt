package matic.apps.maticgithubreposampleandroid.api

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import matic.apps.maticgithubreposampleandroid.api.LiveDataTestUtil.getValue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
//import okio.Okio
//import org.hamcrest.CoreMatchers.`is`
//import org.hamcrest.core.IsNull.notNullValue
//import org.junit.After
//import org.junit.Assert.assertThat
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import matic.apps.maticgithubreposampleandroid.api.LiveDataTestUtil.getValue
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
class GithubServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GithubService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Test
    fun search() {
        enqueueResponse("respository_sample")

        val response = getValue(service.searchRepositories()) as ApiSuccessResponse
        assertThat(response, notNullValue())
        assertThat(response.body.total, `is`(41))
        assertThat(response.body.items.size, `is`(30))
        assertThat<String>(
            response.links["next"],
            `is`("https://api.github.com/search/repositories?q=foo&page=2")
        )
        assertThat<Int>(response.nextPage, `is`(2))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
