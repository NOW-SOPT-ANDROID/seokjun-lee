package com.sopt.now.compose.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.impl.AuthRepositoryImpl
import com.sopt.now.compose.container.impl.UserRepositoryImpl
import com.sopt.now.compose.container.repository.AuthRepository
import com.sopt.now.compose.container.repository.UserRepository
import com.sopt.now.compose.network.dto.RequestLoginDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.password,
        isSuccess:Boolean = _uiState.value.isSuccess,
        message: String = _uiState.value.message

    ) {
        _uiState.update { currentState ->
            currentState.copy(
                id = id,
                password = pw,
                isSuccess = isSuccess,
                message = message
            )
        }
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
        password =  _uiState.value.password
    )

    private fun setUserIdInPreference(userId: String) {
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