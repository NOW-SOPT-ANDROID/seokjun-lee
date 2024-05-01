package com.sopt.now.password

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.main.MainState
import com.sopt.now.models.User
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class PasswordState(
    val isSuccess: Boolean,
    val message: String

)

class PasswordViewModel: ViewModel() {
    val liveData = MutableLiveData<PasswordState>()
    private val authService by lazy { ServicePool.mainService }

    fun patchPassword(request: RequestChangePasswordDto) {
        authService.changePassword(request).enqueue(object : Callback<ResponseChangePasswordDto> {
            override fun onResponse(
                call: Call<ResponseChangePasswordDto>,
                response: Response<ResponseChangePasswordDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseChangePasswordDto? = response.body()
                    liveData.value = PasswordState(
                        isSuccess = true,
                        message = data?.message!!
                    )
                } else {
                    val error = response.message()
                    liveData.value = PasswordState(
                        isSuccess = false,
                        message = "회원정보 불러오기 실패 $error")
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