package matic.apps.maticgithubreposampleandroid.api

import matic.apps.maticgithubreposampleandroid.models.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface GithubService {


//    q=created:>2017-10-22&sort=stars&order=desc&page=2
//    q=  created:>2017-10-22
//    sort= stars
//    order= desc
//    page= 2
    @GET("search/repositories")
    fun searchRepos(
        @Query("q") query: String = "",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: String = "1"
    ): Call<RepoSearchResponse>
}
