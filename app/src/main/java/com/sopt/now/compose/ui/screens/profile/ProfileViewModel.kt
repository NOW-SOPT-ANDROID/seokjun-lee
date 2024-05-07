package com.sopt.now.compose.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.ServicePool
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private fun updateUiState(user: User?) {
        if(user != null) {
            _uiState.value = ProfileUiState.Success(user)
        } else {
            _uiState.value = ProfileUiState.Error
        }
    }

    fun getMemberInfo(memberId: String) {
        ServicePool.initMainService(memberId)
        val authService = ServicePool.mainService
        authService.getMemberInfo().enqueue(object : Callback<ResponseMemberInfoDto> {
            override fun onResponse(
                call: Call<ResponseMemberInfoDto>,
                response: Response<ResponseMemberInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseMemberInfoDto? = response.body()
                    if(data?.data != null) {
                        updateUiState(
                            user = User(
                                id = data.data.authenticationId,
                                nickName = data.data.nickname,
                                phone = data.data.phone
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMemberInfoDto>, t: Throwable) {
                Log.d("ProfileViewModel", "서버에러")
            }
        })
    }



    fun onLogoutButtonPressed(navController: NavHostController) {
        navController.navigateUp()
    }

    fun onBackPressed(navController: NavHostController){
        navController.run {
            previousBackStackEntry?.savedStateHandle?.set(NAVIGATE_BACK_PRESSED_KEY, NAVIGATE_BACK_PRESSED_KEY)
            navigateUp()
        }
    }
}