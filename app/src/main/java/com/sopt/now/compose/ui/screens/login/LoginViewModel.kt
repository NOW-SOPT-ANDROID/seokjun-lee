package com.sopt.now.compose.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.compose.R
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.PreferenceUserRepository
import com.sopt.now.compose.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    val userRepository: PreferenceUserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState("", "", -1))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val userList: MutableList<User> = mutableListOf(
        User("test", "test1234", "test", "TEST")
    )

    init {
        fetchUserFromRepository()
    }

    fun clearUiState() {
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
        viewModelScope.launch {
            userRepository.setUserProfile(newUser)
        }
    }
    private fun fetchUserFromRepository() = viewModelScope.launch {
            val user = userRepository.getUserProfile()
            if(user.id.isNotBlank()){
                userList.add(user)
            }
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SoptApplication)
                val userRepository = application.appContainer.userRepository
                LoginViewModel(userRepository = userRepository)
            }
        }
    }
}