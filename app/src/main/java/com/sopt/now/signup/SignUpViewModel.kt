package com.sopt.now.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.login.LoginViewModel
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseSignUpDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class SignUpState(
    val isSuccess: Boolean,
    val message: String
)

class SignUpViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<SignUpState>()

    fun signUp(request: RequestSignUpDto) {
        authService.signUp(request).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    liveData.value = SignUpState(
                        isSuccess = true,
                        message = "회원가입 성공 유저의 ID는 $userId 입니다"
                    )
                    Log.d("SignUp", "data: $data, userId: $userId")
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
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                liveData.value = SignUpState(
                    isSuccess = false,
                    message = "서버에러"
                )
            }
        })
    }
}