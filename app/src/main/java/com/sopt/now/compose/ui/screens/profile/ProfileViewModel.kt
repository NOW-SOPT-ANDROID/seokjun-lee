package com.sopt.now.compose.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.impl.MemberRepositoryImpl
import com.sopt.now.compose.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: MemberRepositoryImpl
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private fun updateUiState(user: User?) {
        if(user != null) {
            _uiState.value = ProfileUiState.Success(user)
        } else {
            _uiState.value = ProfileUiState.Error
        }
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            val result = authRepository.getUserInfo()
            result.fold(
                onSuccess = {
                    updateUiState(
                        user = User(
                            id = it.id,
                            nickName = it.nickName,
                            phone = it.phone
                        )
                    )
                },
                onFailure = {
                    Log.d(TAG, it.message.toString())
                }
            )
        }
    }

    companion object{
        private const val TAG = "ProfileViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication)
                val authRepository = application.appContainer.memberRepository
                ProfileViewModel(authRepository)
            }
        }
    }
}