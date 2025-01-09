package com.skyvo.mobile.core.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.navigation.NavigationEffect
import com.skyvo.mobile.core.base.viewmodel.error.ErrorTypeEnum
import com.skyvo.mobile.core.base.viewmodel.error.GenericErrorModel
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper
import com.skyvo.mobile.core.shared.exception.NetworkException
import com.skyvo.mobile.core.shared.result.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), FetchExtras {

    private val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _removeAllLoading = SingleLiveData<Boolean>()
    val removeAllLoading: LiveData<Boolean> = _removeAllLoading

    private val _error = SingleLiveData<GenericErrorModel>()
    val error: LiveData<GenericErrorModel> = _error

    private val _exception = SingleLiveData<Exception>()
    val exception: LiveData<Exception> = _exception

    private val _effect: Channel<UIEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected fun setEffect(builder: () -> UIEffect) = viewModelScope.launch {
        val effectValue = builder()
        _effect.send(effectValue)
    }

    fun showLoading(overrideLoading: Boolean = false) {
        if (overrideLoading) return
        _loading.value = true
    }

    fun dismissLoading(overrideLoading: Boolean = false, call: () -> Unit) {
        if (!overrideLoading)
            _loading.value = false
        call.invoke()
    }

    fun removeAllLoading() {
        _removeAllLoading.value = true
    }

    fun showGenericError() {
        _error.value = GenericErrorModel(
            type = 1,
            title = null,
            message = null,
            buttonActionType = 0
        )
    }

    fun showErrorMessage(
        type: Int = 1,
        title: String? = null,
        message: String? = null,
        buttonActionType: Int = 0
    ) {
        _error.value = GenericErrorModel(
            type = type,
            title = title,
            message = message,
            buttonActionType = buttonActionType
        )
    }

    fun handleException(exception: Exception?) {
        exception?.let {
            _exception.postValue(it)
        }
    }

    suspend inline fun <T> Flow<NetworkResult<T>>.collectNetworkResult(
        overrideLoading: Boolean = false,
        crossinline onSuccess: (T?) -> Unit,
        noinline onError: ((GenericErrorModel?) -> Unit)? = null,
        crossinline onLoading: () -> Unit = { showLoading(overrideLoading) },
        noinline onComplete: (() -> Unit)? = null
    ) {
        collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    onSuccess.invoke(result.data)
                }

                is NetworkResult.Error -> {
                    if (onError != null) {
                        when (result.error) {
                            is NetworkException.ResultError -> {
                                onError.invoke(
                                    GenericErrorModel(
                                        type = (result.error as NetworkException.ResultError).type
                                            ?: ErrorTypeEnum.GENERIC.type,
                                        message = (result.error as NetworkException.ResultError).errorMessage,
                                        title = (result.error as NetworkException.ResultError).errorTitle
                                    )
                                )
                            }

                            else -> {
                                onError.invoke(
                                    GenericErrorModel(
                                        type = ErrorTypeEnum.GENERIC.type,
                                        message = ""
                                    )
                                )
                            }
                        }
                    } else {
                        handleException(result.error)
                    }
                }

                is NetworkResult.Loading -> {
                    onLoading()
                }

                is NetworkResult.Complete -> {
                    onComplete?.invoke()
                }
            }
        }
    }

    @CallSuper
    override fun fetchExtras(extra: Bundle) {
    }

    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        try {
            ExceptionFBHelper.recordException(Exception(throwable))
        } catch (e: Exception) {
            ExceptionFBHelper.recordException(e)
        }
    }

    fun navigate(navDirections: NavDirections) {
        setEffect {
            NavigationEffect.Navigate(navDirections)
        }
    }

    fun navigate(
        navDeepLink: NavDeeplinkDestination,
        popUpTo: Boolean = false,
        popUpToId: Int = -1,
        popUpToInclusive: Boolean = false,
        enterAnim: Int? = null,
        exitAnim: Int? = null,
    ) {
        setEffect {
            NavigationEffect.NavigateDeeplink(
                navDeeplinkDestination = navDeepLink,
                popUpTo = popUpTo,
                popUpToId = popUpToId,
                popUpToInclusive = popUpToInclusive,
                enterAnim = enterAnim,
                exitAnim = exitAnim
            )
        }
    }

    fun navigateBack(
        @IdRes destinationId: Int? = null,
        result: Bundle? = null,
        requestKey: String? = null,
        enterAnim: Int? = null,
        exitAnim: Int? = null,
    ) {
        setEffect {
            NavigationEffect.NavigateBack(
                destinationId,
                result,
                requestKey,
                enterAnim,
                exitAnim
            )
        }
    }

    fun restartApp() {
        setEffect {
            NavigationEffect.Restart
        }
    }

    fun relaunchApp() {
        setEffect {
            NavigationEffect.RelaunchApp
        }
    }
}