package com.sopt.now.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.ResponseLoginDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginViewModel"

data class LoginState(
    val isSuccess: Boolean,
    val message: String
)
class LoginViewModel: ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<LoginState>()

    fun login(request: RequestLoginDto) {
        authService.postLogin(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
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
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                liveData.value = LoginState(
                    isSuccess = false,
                    message = "서버 에러"
                )
            }
        })
    }

    companion object{
        const val HEADER_NAME = "location"
        const val JSON_NAME = "message"
    }
}