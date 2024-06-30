package com.sopt.now.compose.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.entity.request.SignupRequestEntity
import com.sopt.now.compose.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
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

    fun postSignup() = viewModelScope.launch {
        authRepository.postSignUp(
            SignupRequestEntity(
                authenticationId = _uiState.value.authenticationId,
                password = _uiState.value.password,
                nickname = _uiState.value.nickName,
                phone = _uiState.value.phone
            )
        ).fold(
            onSuccess = {
                updateUiState(
                    isSuccess = true,
                    message = it.memberId
                )
            },
            onFailure = {
                updateUiState(
                    isSuccess = false,
                    message = it.message.toString()
                )
            }
        )
    }
}