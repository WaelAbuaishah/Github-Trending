package matic.apps.maticgithubreposampleandroid.utils.networkingUtils


object ServiceLayerInitializer {

    fun initializeLayer() {
        val headers = AppRetrofitHeaders()
        InitApi.initialize()
        InitApi.getInstance().setRequestHeaders(headers)
    }
}