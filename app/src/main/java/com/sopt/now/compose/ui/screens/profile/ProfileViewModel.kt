package com.sopt.now.compose.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.SoptApplication.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.ext.getDataFromPreviousBackStackEntry
import com.sopt.now.compose.ext.putDataAtPreviousBackStackEntry
import com.sopt.now.compose.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun updateUiState(user: User?) {
        if(user != null) {
            _uiState.value = ProfileUiState.Success(user)
        } else {
            _uiState.value = ProfileUiState.Error
        }
    }
    fun getUserLoggedIn(navController: NavHostController) {
        navController.getDataFromPreviousBackStackEntry<User>(SoptApplication.NAVIGATE_LOGIN_KEY)?.value?.run {
            updateUiState(this)
        }
    }
    fun onBackPressed(navController: NavHostController){
        navController.putDataAtPreviousBackStackEntry(NAVIGATE_BACK_PRESSED_KEY, NAVIGATE_BACK_PRESSED_KEY)
        navController.navigateUp()
    }
}