package com.sopt.now.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.SoptApplication
import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.login.LoginViewModel
import com.sopt.now.network.dto.RequestSignUpDto
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val liveData = MutableLiveData<SignUpState>()

    fun postSignUp(request: RequestSignUpDto) = viewModelScope.launch {
        runCatching {
            authRepository.postSignUp(request)
        }.onSuccess { response ->
            if (response.isSuccessful) {
                val userId = response.headers()[USER_ID_HEADER]
                liveData.value = SignUpState(
                    isSuccess = true,
                    message = "회원가입 성공 유저의 ID는 $userId 입니다"
                )
            } else {
                val error = response.errorBody()?.string()
                if(error != null) {
                    val jsonMessage = Json.parseToJsonElement(error)
                    liveData.value = SignUpState(
                        isSuccess = false,
                        message = jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString()
                    )
                }
            }
        }.onFailure {
            liveData.value = SignUpState(
                isSuccess = false,
                message = "서버에러"
            )
        }
    }

    companion object {
        const val USER_ID_HEADER = "location"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication
                val authRepository = application.appContainer.authRepository
                SignUpViewModel(authRepository = authRepository)
            }
        }
    }
}