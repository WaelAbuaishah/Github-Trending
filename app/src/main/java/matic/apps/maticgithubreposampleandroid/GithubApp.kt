package matic.apps.maticgithubreposampleandroid

import android.app.Application
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ServiceLayerInitializer

class GithubApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLayerInitializer.initializeLayer()
    }
}