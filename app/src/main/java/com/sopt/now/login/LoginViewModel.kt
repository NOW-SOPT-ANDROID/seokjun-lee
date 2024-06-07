package com.sopt.now.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.SoptApplication
import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.network.dto.RequestLoginDto
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

data class LoginState(
    val isSuccess: Boolean,
    val message: String
)
class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    val liveData = MutableLiveData<LoginState>()

    fun postLogin(request: RequestLoginDto) = viewModelScope.launch {
        runCatching {
            authRepository.postLogin(request)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                val userId = response.headers()[HEADER_NAME]
                liveData.value = LoginState(
                    isSuccess = true,
                    message = userId.toString()
                )
            } else {
                val error = response.errorBody()?.string()
                if(error != null){
                    val jsonMessage = Json.parseToJsonElement(error)
                    liveData.value = LoginState(
                        isSuccess = false,
                        message = jsonMessage.jsonObject[JSON_NAME].toString()
                    )
                }
            }
        }.onFailure {
            liveData.value = LoginState(
                isSuccess = false,
                message = "서버 에러"
            )
        }
    }

    companion object{
        const val HEADER_NAME = "location"
        const val JSON_NAME = "message"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as SoptApplication
                val authRepository = application.appContainer.authRepository
                LoginViewModel(authRepository = authRepository)
            }
        }
    }
}