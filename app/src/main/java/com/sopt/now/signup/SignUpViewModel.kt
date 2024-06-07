package com.sopt.now.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.login.LoginViewModel
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseSignUpDto
import com.sopt.now.repository.AuthRepository
import com.sopt.now.repository.AuthRepositoryImpl
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val liveData = MutableLiveData<SignUpState>()

    fun postSignUp(request: RequestSignUpDto) = viewModelScope.launch {
        runCatching {
            authRepository.postSignUp(request)
        }.onSuccess { response ->
            if (response.isSuccessful) {
                val data: ResponseSignUpDto? = response.body()
                val userId = response.headers()["location"]
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
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authRepository = AuthRepositoryImpl(ServicePool.authService)
                SignUpViewModel(authRepository = authRepository)
            }
        }
    }
}