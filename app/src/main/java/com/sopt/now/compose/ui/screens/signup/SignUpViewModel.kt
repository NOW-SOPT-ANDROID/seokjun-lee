package com.sopt.now.compose.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.impl.AuthRepositoryImpl
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class SignUpViewModel(
    private val authRepository: AuthRepositoryImpl
): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    fun updateUiState(
        isSuccess: Boolean = _uiState.value.isSuccess,
        message: String = _uiState.value.message,
        authenticationId: String = _uiState.value.authenticationId,
        password: String = _uiState.value.password,
        nickName: String = _uiState.value.nickName,
        phone: String = _uiState.value.phone,
    ) {
        _uiState.update {currentState ->
            currentState.copy(
                isSuccess = isSuccess,
                message = message,
                authenticationId = authenticationId,
                password = password,
                nickName = nickName,
                phone = phone)
        }
    }
    fun patchSignUp(request: RequestSignUpDto = getRequestSignUpDto()) {
        viewModelScope.launch {
            authRepository.postSignUp(request).fold(
                onSuccess = {
                    if(it.isSuccessful){
                        val userId = it.headers()["location"]
                        updateUiState(
                            isSuccess = true,
                            message = userId.toString()
                        )
                    } else {
                        val error = it.errorBody()?.string()
                        if (error != null) {
                            val jsonMessage = Json.parseToJsonElement(error)
                            updateUiState(
                                isSuccess = false,
                                message = jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString()
                            )
                        }
                    }
                },
                onFailure = {
                    updateUiState(
                        isSuccess = false,
                        message = "서버에러"
                    )
                }
            )
        }
    }

    private fun getRequestSignUpDto(): RequestSignUpDto = with(_uiState.value) {
        RequestSignUpDto(
            authenticationId = this.authenticationId,
            password = this.password,
            nickname = this.nickName,
            phone = this.phone
        )
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication)
                val authRepository = application.appContainer.authRepository
                SignUpViewModel(authRepository = authRepository)
            }
        }
    }

}