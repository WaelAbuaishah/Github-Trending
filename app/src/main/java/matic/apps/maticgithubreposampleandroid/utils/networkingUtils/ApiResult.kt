package matic.apps.maticgithubreposampleandroid.utils.networkingUtils

// THIS CLASS is a layer for getting result from the cache files or from the internet
class ApiResult<T>(var result: T? = null,
                   var error: String? = null,
                   var isOffline: Boolean = false,
                   val type: Type? = null) {

    enum class Type {
        REPOSITORIES_RESULT
    }
}