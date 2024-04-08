package com.sopt.now.compose.ui.screens.signup

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState: StateFlow<User> = _uiState.asStateFlow()

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.pw,
        nickName: String = _uiState.value.nickName,
        mbti: String = _uiState.value.mbti
    ) {
        _uiState.update {currentState ->
            currentState.copy( id = id, pw = pw, nickName = nickName, mbti = mbti )
        }
    }

    fun checkSignUp(context: Context): Boolean {
        var toastStringRes: Int? = null
        val isPossible = when {
            _uiState.value.id.length !in 6..10 -> {
                toastStringRes = R.string.signup_toast_id
                false
            }

            _uiState.value.pw.length !in 8..12 -> {
                toastStringRes = R.string.signup_toast_pw
                false
            }

            _uiState.value.nickName.isBlank() -> {
                toastStringRes = R.string.signup_toast_nickname
                false
            }

            _uiState.value.mbti.isBlank() -> {
                toastStringRes = R.string.signup_toast_mbti
                false
            }
            else -> {
                true
            }
        }
        if(toastStringRes != null) {
            Toast.makeText(context, ContextCompat.getString(context, toastStringRes), Toast.LENGTH_SHORT).show()
        }
        return isPossible
    }

}