package com.sopt.now.compose.ui.screens.login

import androidx.lifecycle.ViewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState("", "", -1))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val userList: MutableList<User> = mutableListOf(
        User("test", "test1234", "test", "TEST")
    )

    fun initializeUiState() {
        updateUiState(id = "", pw = "", userIndex = -1)
    }

    fun updateUiState(
        id: String = _uiState.value.id,
        pw: String = _uiState.value.pw,
        userIndex: Int = _uiState.value.userIndex
    ) {
        _uiState.update {currentState ->
            currentState.copy(
                id = id,
                pw = pw,
                userIndex = userIndex
            )
        }
    }

    fun getUser(): User = userList[_uiState.value.userIndex]
    fun addUsers(newUser: User){
        userList.add(newUser)
    }

    fun isLoginPossible(): Boolean = (_uiState.value.userIndex != -1)

    fun getToastMessageByCheckingIdAndPw(): Int {
        var toastMessageResId:Int = R.string.login_toast_blank_id
        when{
            _uiState.value.id.isBlank() -> {toastMessageResId = R.string.login_toast_blank_id}
            _uiState.value.pw.isBlank() -> {toastMessageResId = R.string.login_toast_blank_pw}
            else -> {
                userList.forEach { user ->
                    when{
                        user.id == _uiState.value.id && user.pw == _uiState.value.pw -> {
                            _uiState.value.userIndex = userList.indexOf(user)
                            toastMessageResId = R.string.login_toast_success_login
                            return@forEach
                        }
                        user.id != _uiState.value.id -> {
                            toastMessageResId = R.string.login_toast_check_id
                        }
                        user.pw != _uiState.value.pw -> {
                            toastMessageResId = R.string.login_toast_check_pw
                        }
                    }
                }
            }
        }
        return toastMessageResId
    }
}