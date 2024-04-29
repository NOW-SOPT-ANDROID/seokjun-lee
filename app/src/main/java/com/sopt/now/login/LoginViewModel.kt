package com.sopt.now.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseLoginDto
import com.sopt.now.network.dto.ResponseSignUpDto
import com.sopt.now.signup.SignUpState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class LoginState(
    val isSuccess: Boolean,
    val message: String,
    val memberId: String? = null
)
class LoginViewModel: ViewModel() {

    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<LoginState>()

    fun login(request: RequestLoginDto) {
        authService.login(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()["location"]
                    liveData.value = LoginState(
                        isSuccess = true,
                        message = "로그인 성공 (유저 ID: $userId)",
                        memberId = userId
                    )
                    Log.d("Login", "data: $data, userId: $userId")
                } else {
                    val error = response.message()
                    liveData.value = LoginState(
                        isSuccess = false,
                        message = "로그인이 실패 $error"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                liveData.value = LoginState(
                    isSuccess = false,
                    message = "서버에러"
                )
            }
        })
    }
}