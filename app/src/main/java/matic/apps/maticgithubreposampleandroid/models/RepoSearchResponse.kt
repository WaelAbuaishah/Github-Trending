package matic.apps.maticgithubreposampleandroid.models

import com.google.gson.annotations.SerializedName

/**
 * Simple object to hold repo search responses.
 */
data class RepoSearchResponse(
    @SerializedName("total_count")
    public val total: Int = 0,
    @SerializedName("items")
    public val items: List<Repo>
) {
    var nextPage: Int? = null
}
