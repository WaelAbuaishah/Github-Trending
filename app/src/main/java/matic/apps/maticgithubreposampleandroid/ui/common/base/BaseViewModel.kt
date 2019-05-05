package matic.apps.maticgithubreposampleandroid.ui.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ApiResult

open class BaseViewModel<T> : ViewModel(){
    var apiResult : MutableLiveData<ApiResult<T>> = MutableLiveData()
}