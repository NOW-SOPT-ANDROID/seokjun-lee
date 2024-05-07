package com.sopt.now.compose.ui.screens.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_LOGIN_KEY
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.AuthRepository
import com.sopt.now.compose.container.NetworkAuthRepository
import com.sopt.now.compose.container.PreferenceUserRepository
import com.sopt.now.compose.network.ServicePool.temporaryAuthService
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.ui.navigation.HomeDestination
import com.sopt.now.compose.ui.navigation.SignUpDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val userRepository: PreferenceUserRepository,
    private val authRepository: NetworkAuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.pw,
        userIndex: Int = _uiState.value.userIndex,
        isSuccess:Boolean = _uiState.value.isSuccess,
        message: String = _uiState.value.message

    ) {
        _uiState.update { currentState ->
            currentState.copy(
                id = id,
                pw = pw,
                userIndex = userIndex,
                isSuccess = isSuccess,
                message = message
            )
        }
    }

    fun onSignUpButtonClicked(navController: NavHostController) {
        navController.navigate(SignUpDestination.route)
    }

    fun postLogin(request: RequestLoginDto = getRequestLoginDto()) {
        viewModelScope.launch {
            authRepository.postLogin(request).fold(
                onSuccess = {
                    if(it.isSuccessful){
                        val userId = it.headers()[HEADER_NAME]
                        updateUiState(
                            isSuccess = true,
                            message = userId.toString()
                        )
                        setUserIdInPreference(userId = userId.toString())
                    } else {
                        val error = it.errorBody()?.string()
                        if (error != null) {
                            val jsonMessage = Json.parseToJsonElement(error)

                            updateUiState(
                                isSuccess = false,
                                message = jsonMessage.jsonObject[JSON_NAME].toString()
                            )
                        }
                    }
                },
                onFailure = {
                    updateUiState(
                        isSuccess = false,
                        message = "서버 에러"
                    )
                }
            )
        }
    }

    private fun getRequestLoginDto(): RequestLoginDto = RequestLoginDto(
        authenticationId = _uiState.value.id,
        password =  _uiState.value.pw
    )

    private fun setUserIdInPreference(userId: String) = viewModelScope.launch {
        userRepository.setUserId(userId)
    }

    companion object {
        const val HEADER_NAME = "location"
        const val JSON_NAME = "message"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SoptApplication)
                val userRepository = application.appContainer.userRepository
                val authRepository = application.appContainer.authRepository
                LoginViewModel(userRepository = userRepository, authRepository = authRepository)
            }
        }
    }
}