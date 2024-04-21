package com.sopt.now.compose.ui.screens.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_LOGIN_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_SIGNUP_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.PreferenceUserRepository
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.navigation.HomeDestination
import com.sopt.now.compose.ui.navigation.SignUpDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    val userRepository: PreferenceUserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val userList: MutableList<User> = mutableListOf(
        User("test", "test1234", "test", "TEST")
    )

    init {
        fetchUserFromPreferenceRepository()
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

    fun onSignUpButtonClicked(navController: NavHostController){
        navController.navigate(SignUpDestination.route)
    }

    fun onLoginButtonClicked(navController: NavHostController) {
        if(_uiState.value.userIndex != -1) {
            navigateToHome(navController)
        }
    }

    fun getToastMessageByCheckingIdAndPw(): Int {
        var toastMessageResId:Int = R.string.login_toast_success_login
        when{
            _uiState.value.id.isBlank() -> {toastMessageResId = R.string.login_toast_blank_id}
            _uiState.value.pw.isBlank() -> {toastMessageResId = R.string.login_toast_blank_pw}
            else -> {
                userList.forEach { user ->
                    when{
                        user.id == _uiState.value.id && user.pw == _uiState.value.pw -> {
                            _uiState.value.userIndex = userList.indexOf(user)
                            return@forEach
                        }
                        user.id == _uiState.value.id && user.pw != _uiState.value.pw -> {
                            toastMessageResId = R.string.login_toast_check_pw
                            return@forEach
                        }
                        userList.indexOf(user) == userList.size-1 -> {
                            toastMessageResId = R.string.login_toast_check_id
                        }
                    }
                }

            }
        }
        return toastMessageResId
    }

    fun checkCurrentStack(context: Context, navController: NavHostController) {
        navController.currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<User>(NAVIGATE_SIGNUP_KEY).value?.run{
                setUserInPreferenceRepository(this)
                userList.add(this)
            }

            getLiveData<String>(NAVIGATE_BACK_PRESSED_KEY).value?.run{
                if(this == NAVIGATE_BACK_PRESSED_KEY) endApplication(context)
            }
        }
    }

    private fun endApplication(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }

    private fun navigateToHome(navController: NavHostController) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = NAVIGATE_LOGIN_KEY,
            value = userList[_uiState.value.userIndex]
        )
        navController.navigate(HomeDestination.route)
        updateUiState(id = "", pw = "", userIndex = -1)
    }

    private fun setUserInPreferenceRepository(newUser: User){
        viewModelScope.launch {
            userRepository.setUserProfile(newUser)
        }
    }
    private fun fetchUserFromPreferenceRepository() = viewModelScope.launch {
        val user = userRepository.getUserProfile()
        if(user.id.isNotBlank()){
            userList.add(user)
        }
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