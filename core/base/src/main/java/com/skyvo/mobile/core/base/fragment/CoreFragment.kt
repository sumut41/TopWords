package com.skyvo.mobile.core.base.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.skyvo.mobile.core.base.R
import com.skyvo.mobile.core.base.firebase.FirebaseAnalyticsHelper
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.base.navigation.navigateBackWithResult
import com.skyvo.mobile.core.base.navigation.relaunchApp
import com.skyvo.mobile.core.base.navigation.restart
import com.skyvo.mobile.core.base.loading.ILoadingDialogManager
import com.skyvo.mobile.core.base.loading.LoadingDialogManager
import com.skyvo.mobile.core.base.navigation.NavigationEffect
import com.skyvo.mobile.core.base.viewmodel.error.ErrorTypeEnum
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper
import com.skyvo.mobile.core.shared.exception.NetworkException
import timber.log.Timber

abstract class CoreFragment : Fragment(), ILoadingDialogManager by LoadingDialogManager() {

    abstract var displayBottomNavigationBarMenu: Boolean
    protected open val onBackPressed: () -> Unit = {
        navigateBack()
    }

    protected open fun observeListener() {}

    protected open fun onViewReady(bundle: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clearSecureFlag()
        nameLogCat()
        createLoadingDialog(
            context = requireContext(),
            layoutResId = R.layout.layout_loading_view,
        )
    }

    private fun nameLogCat() {
        Timber.d("\uD83D\uDCDD=screenPage=\uD83D\uDCDD" + this::class.java.name)
        FirebaseAnalyticsHelper(requireContext()).logScreenView(
            screenName = this::class.java.simpleName,
            screenClass = this::class.java.name
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeListener()
        onViewReady(savedInstanceState)
    }

    protected fun onLoading(isShow: Boolean) {
        if (isShow) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    protected fun removeAllLoading() {
        removeOnAllLoading()
    }

    protected fun showErrorMessage(
        type: Int,
        title: String? = null,
        message: String? = null,
        buttonActionType: Int = 0
    ) {
        try {
            hideLoading()
            when (type) {
                ErrorTypeEnum.GENERIC.type -> {
                    /*
                    TODO: Implement this ErrorFullScreen
                    navigate(
                        NavDeeplinkDestination.ErrorFullScreen(
                            title = title ?: "",
                            message = message ?: "",
                        )
                    ) */
                }

                ErrorTypeEnum.FRIENDLY.type -> {
                    /*
                    TODO: Implement this ErrorBottomSheetDialog
                    navigate(
                        NavDeeplinkDestination.ErrorBottomSheetDialog(
                            title = title ?: "",
                            message = message ?: "",
                            actionType = GenericButtonActionType.UNDEFINED.type
                        )
                    ) */
                }

                ErrorTypeEnum.FORCE_UPDATE.type -> {
                    /*
                    TODO: Implement this
                    navigate(
                        NavDeeplinkDestination.ErrorBottomSheetDialog(
                            title = title ?: "",
                            message = message ?: "",
                            actionType = GenericButtonActionType.GO_PLAY_STORE.type
                        )
                    ) */
                }

                ErrorTypeEnum.AUTHORIZATION.type -> {
                    /*
                    TODO: Implement this ErrorFullScreen
                    navigate(
                        NavDeeplinkDestination.ErrorFullScreen(
                            title = title ?: "",
                            message = message ?: "",
                            actionType = GenericButtonActionType.LOGIN.type
                        )
                    ) */
                }

                ErrorTypeEnum.STORE_SERVICE_PROVIDER.type -> {
                    /*
                    TODO: Implement this
                    navigate(
                        NavDeeplinkDestination.ErrorBottomSheetDialog(
                            title = title ?: "",
                            message = message ?: "",
                            actionType = GenericButtonActionType.EXIT_APP.type
                        )
                    ) */
                }
            }
        } catch (ex: Exception) {
            /*
            TODO: Implement this
            NavDeeplinkDestination.ErrorFullScreen(
                title = title ?: "",
                message = message,
                actionType = GenericButtonActionType.EXIT_APP.type
            )
            */
        }
    }

    protected fun handleException(exception: Exception) {
        when (exception) {
            is NetworkException.ResultError -> {
                showErrorMessage(
                    type = exception.type ?: ErrorTypeEnum.GENERIC.type,
                    message = exception.errorMessage,
                    title = exception.errorTitle
                )
            }

            is NetworkException.NoInternetConnection -> {
                showErrorMessage(
                    type = ErrorTypeEnum.GENERIC.type,
                    message = "Uygulamamızı kullanabilmek için cihazınızın internete bağlı olması lazım.",
                    title = "İnternete Bağlanılamıyor"
                )
            }

            is NetworkException.TimeOutError -> {
                showErrorMessage(
                    type = ErrorTypeEnum.GENERIC.type,
                    message = "İnternet bağlantısı sorunu nedeniyle işlemine devam edemiyoruz.",
                    title = "Uyarı"
                )
            }

            is NetworkException.ParsingJsonError -> {
                showErrorMessage(
                    type = ErrorTypeEnum.GENERIC.type,
                    message = "İşlemin sırasında beklenmedik bir hata oluştu. Lütfen daha sonra tekrar dene.",
                    title = "Şu anda işlemini gerçekleştiremiyoruz."
                )
                recordException(exception)
            }

            else -> {
                showErrorMessage(
                    type = ErrorTypeEnum.GENERIC.type,
                    message = "Şu anda işlemini gerçekleştiremiyoruz. Lütfen daha sonra tekrar deneyiniz.",
                    title = "Hata oluştu."
                )
                recordException(exception)
            }
        }
    }

    protected fun onNavigationEffect(effect: NavigationEffect) {
        when (effect) {
            is NavigationEffect.Navigate -> {
                navigate(effect.destination)
            }

            is NavigationEffect.NavigateDeeplink -> {
                navigate(
                    navDeeplinkDestination = effect.navDeeplinkDestination,
                    popUpToInclusive = effect.popUpToInclusive,
                    popUpTo = effect.popUpTo,
                    popUpToId = effect.popUpToId
                )
            }

            is NavigationEffect.NavigateWithGraph -> {
                navigate(
                    destinationId = effect.destinationId,
                    graphResIdRes = effect.graphResId
                )
            }

            is NavigationEffect.NavigateBack -> {
                if (effect.result == null || effect.requestKey == null) {
                    navigateBack(destinationId = effect.destinationId)
                } else {
                    navigateBackWithResult(
                        destination = effect.destinationId,
                        result = effect.result,
                        requestKey = effect.requestKey
                    )
                }
            }

            NavigationEffect.RelaunchApp -> relaunchApp()
            NavigationEffect.Restart -> restart()
        }
    }

    fun setKeyboardInputMode(
        inputMode: SoftKeyboardInputEnum,
        inputModeSecond: SoftKeyboardInputEnum? = null
    ) {
        if (inputModeSecond != null) {
            requireActivity().window.setSoftInputMode(inputMode.value or inputModeSecond.value)
        } else {
            requireActivity().window.setSoftInputMode(inputMode.value)
        }
    }

    fun recordException(ex: Exception, logParams: Map<String, String>? = null) {
        ExceptionFBHelper.recordException(ex, logParams)
    }

    fun openBrowserUrl(url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.addCategory(Intent.CATEGORY_BROWSABLE)
            i.setData(Uri.parse(url))
            requireActivity().startActivity(i)
        } catch (ex: Exception) {
            recordException(ex)
        }
    }

    fun addSecureFlag() {
        try {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } catch (ex: Exception) {
            recordException(ex)
        }
    }

    fun clearSecureFlag() {
        try {
            requireActivity().window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } catch (ex: Exception) {
            recordException(ex)
        }
    }
}