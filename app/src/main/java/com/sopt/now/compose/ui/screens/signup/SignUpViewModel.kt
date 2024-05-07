package com.sopt.now.compose.ui.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.sopt.now.compose.network.ServicePool.temporaryAuthService
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel: ViewModel() {
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

     fun onSignUpButtonClicked(navController: NavHostController) {
         val request = getRequestSignUpDto()
         signUp(navController, request)

    }

    private fun signUp(navController:NavHostController, request: RequestSignUpDto) {
        temporaryAuthService.signUp(request).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    updateUiState(
                        isSuccess = true,
                        message = userId.toString()
                    )
                    navController.navigateUp()
                } else {
                    val error = response.errorBody()?.string()

                    if (error != null) {
                        val jsonMessage = Json.parseToJsonElement(error)
                        updateUiState(
                            isSuccess = false,
                            message = jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString()
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                updateUiState(
                    isSuccess = false,
                    message = "서버에러"
                )
                Log.d("SignUpViewModel", _uiState.value.message)
            }
        })
    }

    private fun getRequestSignUpDto(): RequestSignUpDto = with(_uiState.value) {
        return@with RequestSignUpDto(
            authenticationId = this.authenticationId,
            password = this.password,
            nickname = this.nickName,
            phone = this.phone
        )
    }

}