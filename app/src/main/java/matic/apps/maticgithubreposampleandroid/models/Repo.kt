package matic.apps.maticgithubreposampleandroid.models

import com.google.gson.annotations.SerializedName

/**
 * Full repo data
 */
data class Repo(
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("owner")
    val owner: Owner,

    @field:SerializedName("stargazers_count")
    val stars: Int
) {

    data class Owner(
        @field:SerializedName("login")
        val login: String,

        @field:SerializedName("url")
        val url: String?,

        @field:SerializedName("avatar_url")
        val avatarUrl: String?
    )

    companion object {
        const val UNKNOWN_ID = -1
    }
}
