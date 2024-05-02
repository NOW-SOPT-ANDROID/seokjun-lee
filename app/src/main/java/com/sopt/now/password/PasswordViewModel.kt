package com.sopt.now.password

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.main.MainState
import com.sopt.now.models.User
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 비밀번호 변경 시 오류 메시지를 서버 응답에서 가져오는 방법 알아보기
 */

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
                    val error = response.errorBody().toString()
                    val error1 = response.message().toString()
                    val error2 = response.code().toString()
                    val error3 = response.headers().toString()
                    val error4 = response.body()?.message.toString()
                    val data = response.body()
                    liveData.value = PasswordState(
                        isSuccess = false,
                        message = "$error\n$error1\n$error2\n$error3\n$error4\n$data"
                    )
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