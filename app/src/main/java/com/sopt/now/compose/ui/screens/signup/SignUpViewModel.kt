package com.sopt.now.compose.ui.screens.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.MainActivity.Companion.printToastMessage
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.ServicePool.authService
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import com.sopt.now.compose.ui.screens.home.HomeUiState
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        authService.signUp(request).enqueue(object : Callback<ResponseSignUpDto> {
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