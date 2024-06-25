package com.sopt.now.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.SoptApplication
import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.login.LoginViewModel
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class PasswordViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    val liveData = MutableLiveData<PasswordState>()

    fun updatePassword(request: RequestChangePasswordDto) = viewModelScope.launch {
        runCatching {
            authRepository.patchMemberPassword(request)
        }.onSuccess {response ->
            if (response.isSuccessful) {
                val data: ResponseChangePasswordDto? = response.body()
                liveData.value = PasswordState(
                    isSuccess = true,
                    message = data?.message.orEmpty()
                )
            } else {
                val error = response.errorBody()?.string()

                if(error != null) {
                    val jsonMessage = Json.parseToJsonElement(error)

                    liveData.value = PasswordState(
                        isSuccess = false,
                        message = jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString()
                    )
                }
            }
        }.onFailure {
            liveData.value = PasswordState(
                isSuccess = false,
                message = "서버에러"
            )
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication
                val authRepository = application.appContainer.authAfterLoinRepository
                PasswordViewModel(authRepository = authRepository)
            }
        }
    }

}