package matic.apps.maticgithubreposampleandroid.ui.trending

import matic.apps.maticgithubreposampleandroid.api.*
import matic.apps.maticgithubreposampleandroid.models.Repo
import matic.apps.maticgithubreposampleandroid.models.RepoSearchResponse
import matic.apps.maticgithubreposampleandroid.ui.common.base.BaseViewModel
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ApiResult
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.InitApi
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ResponseInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Shows the Trending screen.
 */
class TrendingViewModel : BaseViewModel<List<Repo>>() {
    private var requiredPage = 1
    var isLoading = false

    /*
    * This method to get the query and sent to the view
    */
    fun getGithubRepository(pageNumber: Int = 1, isNextRequired: Boolean = false) {

        if (isNextRequired) {
            requiredPage++
        } else {
            requiredPage = pageNumber
        }

        val apiService = InitApi.getRetrofitInstance().create(GithubService::class.java)
        val call = apiService.searchRepos(
            query = getSearchQuery(),
            page = requiredPage.toString()
        )

        isLoading = true

        RetrofitNetworking().call(object : ResponseInterface<RepoSearchResponse> {
            override fun onSuccess(response: ApiResponse<RepoSearchResponse>?) {
                println("TrendingViewModel.onSuccess")
                isLoading = false
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
                isLoading = false
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
                isLoading = false
                apiResult.value = ApiResult(result = null, error = "onGeneralError")
            }

            override fun onOtherError(error: Throwable) {
                println("TrendingViewModel.onOtherError")

                // this call back created to take an action like, force logout user if session expired
                isLoading = false
                apiResult.value = ApiResult(result = null, error = "Something wrong happened!")
            }
        }, call)
    }

    private fun getSearchQuery(): String {
        // here we need to get format
        val currentDate = Calendar.getInstance()
        val queryRequiredDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        return queryRequiredDateFormat.format(currentDate.time)
    }
}
