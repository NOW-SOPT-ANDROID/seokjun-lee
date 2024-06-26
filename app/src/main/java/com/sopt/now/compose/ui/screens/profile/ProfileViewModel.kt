package com.sopt.now.compose.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.container.impl.MemberRepositoryImpl
import com.sopt.now.compose.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
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
    }
}