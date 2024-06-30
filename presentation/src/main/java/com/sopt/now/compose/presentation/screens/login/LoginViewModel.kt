package com.sopt.now.compose.presentation.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.entity.request.LoginRequestEntity
import com.sopt.now.compose.domain.repository.AuthRepository
import com.sopt.now.compose.domain.repository.HomeRepository
import com.sopt.now.compose.presentation.ui.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.password,
        isSuccess: Boolean = _uiState.value.isSuccess,
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

    fun postLogin() = viewModelScope.launch {
        authRepository.postLogin(
            LoginRequestEntity(
                authenticationId = _uiState.value.id,
                password = _uiState.value.password
            )
        ).fold(
            onSuccess = {
                updateUiState(
                    isSuccess = true,
                    message = it.memberId
                )
                putUserIdInPreference(userId = it.memberId)
            },
            onFailure = {
                updateUiState(
                    isSuccess = false,
                    message = it.message.toString()
                )
            }
        )
    }

    private fun putUserIdInPreference(userId: String) {
        authRepository.putUserIdInPreference(userId)
    }
}