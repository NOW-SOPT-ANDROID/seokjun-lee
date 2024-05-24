package com.sopt.now.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.login.LoginViewModel
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordViewModel: ViewModel() {
    val liveData = MutableLiveData<PasswordState>()
    private val authService by lazy { ServicePool.mainService }

    

    fun patchPassword(request: RequestChangePasswordDto) {
        authService.patchPassword(request).enqueue(object : Callback<ResponseChangePasswordDto> {
            override fun onResponse(
                call: Call<ResponseChangePasswordDto>,
                response: Response<ResponseChangePasswordDto>,
            ) {
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
            }

            override fun onFailure(call: Call<ResponseChangePasswordDto>, t: Throwable) {
                liveData.value = PasswordState(
                    isSuccess = false,
                    message = "서버에러"
                )
            }
        })
    }

}