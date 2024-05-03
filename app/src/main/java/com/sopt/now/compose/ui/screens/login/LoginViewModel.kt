package com.sopt.now.compose.ui.screens.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_LOGIN_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_SIGNUP_KEY
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.PreferenceUserRepository
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.ServicePool.authService
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.ui.navigation.HomeDestination
import com.sopt.now.compose.ui.navigation.SignUpDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.pw,
        userIndex: Int = _uiState.value.userIndex
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                id = id,
                pw = pw,
                userIndex = userIndex
            )
        }
    }

    fun onSignUpButtonClicked(navController: NavHostController) {
        navController.navigate(SignUpDestination.route)
    }

    fun onLoginButtonClicked(navController: NavHostController) {
        val request = getRequestLoginDto()
        login(navController, request)
        Log.d("test", "hello")
    }

    private fun login(navController: NavHostController, request: RequestLoginDto) {
        authService.login(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()[HEADER_NAME]
                    with(_uiState.value) {
                        isSuccess = true
                        message = userId.toString()
                    }
                    navigateToHome(navController)
                } else {
                    val error = response.errorBody()?.string()

                    if (error != null) {
                        val jsonMessage = Json.parseToJsonElement(error)
                        with(_uiState.value) {
                            isSuccess = false
                            message = jsonMessage.jsonObject[JSON_NAME].toString()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                with(_uiState.value) {
                    isSuccess = false
                    message = "서버 에러"
                }
            }
        })
    }

    private fun getRequestLoginDto(): RequestLoginDto = RequestLoginDto(
        authenticationId = _uiState.value.id,
        password =  _uiState.value.pw
    )

    fun checkCurrentStack(context: Context, navController: NavHostController) {
        navController.currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<String>(NAVIGATE_BACK_PRESSED_KEY).value?.run{
                if(this == NAVIGATE_BACK_PRESSED_KEY) endApplication(context)
            }
        }
    }

    private fun endApplication(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }

    private fun navigateToHome(navController: NavHostController) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = NAVIGATE_LOGIN_KEY,
            value = _uiState.value.message
        )
        navController.navigate(HomeDestination.route)
        updateUiState(id = "", pw = "", userIndex = -1)
    }

    /*private fun setUserInPreferenceRepository(newUser: User){
        viewModelScope.launch {
            userRepository.setUserProfile(newUser)
        }
    }
    private fun fetchUserFromPreferenceRepository() = viewModelScope.launch {
        val user = userRepository.getUserProfile()
        if(user.id.isNotBlank()){
            userList.add(user)
        }
    }*/

    companion object {
        const val HEADER_NAME = "location"
        const val JSON_NAME = "message"
    }
}