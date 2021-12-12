package com.example.diplomstrava.presentation.auth

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.diplomstrava.R
import com.example.diplomstrava.data.auth.AuthRepository
import com.example.diplomstrava.presentation.ScreenState
import com.example.diplomstrava.utils.SingleLiveEvent
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val openAuthPageLiveEvent = SingleLiveEvent<Intent>()
    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.DefaultState)

    val state: LiveData<ScreenState>
        get() = stateLiveData

    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent


    fun onAuthCodeFailed() {
        stateLiveData.postValue(ScreenState.CancelState)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        stateLiveData.postValue(ScreenState.LoadingState)
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                stateLiveData.postValue(ScreenState.SuccessState)
                //stateLiveData.postValue(ScreenState.DefaultState)
            },
            onError = {
                stateLiveData.postValue(ScreenState.ErrorState)
                //stateLiveData.postValue(ScreenState.DefaultState)
            }
        )
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(getApplication(), R.color.dark_gray))
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
            customTabsIntent
        )

        stateLiveData.postValue(ScreenState.LoadingState)
        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}