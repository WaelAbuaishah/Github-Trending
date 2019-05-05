package matic.apps.maticgithubreposampleandroid.ui.setting

import matic.apps.maticgithubreposampleandroid.ui.common.base.BaseViewModel
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ApiResult


class SettingsViewModel : BaseViewModel<String>() {


    fun getDummySettingData() {
        apiResult.value = ApiResult(result = "Welcome to the app")
    }
}
