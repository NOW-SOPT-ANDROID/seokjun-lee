package com.sopt.now.compose.ui.screens.signup

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.MainActivity.Companion.printToastMessage
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
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

    fun onSignUpButtonClicked(context: Context, navController: NavHostController) {
        val toastStringRes = getToastMessageByCheckingSignup()
        printToastMessage(context, toastStringRes)
        if(toastStringRes == R.string.login_toast_success_signup) {
            navController.run {
                previousBackStackEntry?.savedStateHandle?.set(
                    MainActivity.NAVIGATE_SIGNUP_KEY,
                    _uiState.value
                )
                navigateUp()
            }
        }
    }

    fun getToastMessageByCheckingSignup(): Int {
        return when {
            _uiState.value.id.length !in ID_MIN_LEN..ID_MAX_LEN -> {
                R.string.signup_toast_id
            }

            _uiState.value.pw.length !in PW_MIN_LEN..PW_MAX_LEN -> {
                R.string.signup_toast_pw
            }

            _uiState.value.nickName.isBlank() -> {
                R.string.signup_toast_nickname
            }

            _uiState.value.mbti.isBlank() -> {
                R.string.signup_toast_mbti
            }
            else -> {
                R.string.login_toast_success_signup
            }
        }
    }

    companion object{
        const val ID_MAX_LEN = 10
        const val ID_MIN_LEN = 6
        const val PW_MAX_LEN = 12
        const val PW_MIN_LEN = 8
    }

}