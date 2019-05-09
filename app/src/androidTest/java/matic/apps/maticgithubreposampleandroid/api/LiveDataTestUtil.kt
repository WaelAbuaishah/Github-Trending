/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package matic.apps.maticgithubreposampleandroid.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {

    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }

/*
    fun getValue(): MutableLiveData<ApiResult<List<Repo>>> {

        val apiService = InitApi.getRetrofitInstance().create(GithubService::class.java)
        val call = apiService.searchRepos(
            query = "created:>2017-10-22&sort=stars&order=desc"
        )
        val apiResult = MutableLiveData<ApiResult<List<Repo>>>()
        RetrofitNetworking().call(object : ResponseInterface<RepoSearchResponse> {
            override fun onSuccess(response: ApiResponse<RepoSearchResponse>?) {
                println("TrendingViewModel.onSuccess")
                when (response) {
                    is ApiSuccessResponse -> {
                        apiResult.value = ApiResult(result = response.body.items)
                    }

                    is ApiErrorResponse -> {
                        apiResult.value = ApiResult(result = null, error = response.errorMessage)
                    }

                    is ApiEmptyResponse -> {
                        apiResult.value = ApiResult(result = ArrayList())
                    }

                    else -> {
                        apiResult.value = ApiResult(result = null, error = "Something wrong happened!")
                    }
                }
            }

            override fun onError(response: ApiResponse<RepoSearchResponse>?) {
                println("TrendingViewModel.onError")
                when (response) {
                    is ApiErrorResponse -> {
                        apiResult.value = ApiResult(result = null, error = response.errorMessage)
                    }

                    else -> {
                        apiResult.value = ApiResult(result = null, error = "Something wrong happened!")
                    }
                }
            }

            override fun onGeneralError(error: Throwable) {
                println("TrendingViewModel.onGeneralError")

                // customize error message and show it to the user
                apiResult.value = ApiResult(result = null, error = "onGeneralError")
            }

            override fun onOtherError(error: Throwable) {
                println("TrendingViewModel.onOtherError")

                // this call back created to take an action like, force logout user if session expired
                apiResult.value = ApiResult(result = null, error = "Something wrong happened!")
            }
        }, call)
      return apiResult
    }
*/
}
