package com.sopt.now.compose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_LOGIN_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.ext.getDataFromPreviousBackStackEntry
import com.sopt.now.compose.ext.putDataAtPreviousBackStackEntry
import com.sopt.now.compose.models.Friend
import com.sopt.now.compose.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val mockFriendList = listOf<Friend>(
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지? ㅎㅎ 아직 반도 안왔어 ^&^",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "최준서",
            selfDescription = "애들아 힘내보자고~!!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "이유빈",
            selfDescription = "나보다 귀여운 사람 나와봐",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "김언지",
            selfDescription = "오비의 맛을 보여줘야겠고만!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "박동민",
            selfDescription = "컴포즈는 나에게 맡기라고!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "배찬우",
            selfDescription = "마지막 20대를 불태워 보겠어..",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "송혜음",
            selfDescription = "소주잔 3조 화이팅!!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "박유진",
            selfDescription = "코리조는 3조가 최고~",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "이석준",
            selfDescription = "내가 제일 잘나가ㅏ",
        )
    )

    fun updateUiState(user: User?) {
        if(user != null) {
            _uiState.value = HomeUiState.Success(user)
        } else {
            _uiState.value = HomeUiState.Error
        }
    }

    fun fetchUserLoggedIn(navController: NavHostController) {
        navController.getDataFromPreviousBackStackEntry<User>(NAVIGATE_LOGIN_KEY)?.value?.run {
            updateUiState(this)
        }
    }
    fun onBackPressed(navController: NavHostController){
        navController.putDataAtPreviousBackStackEntry(
            NAVIGATE_BACK_PRESSED_KEY,
            NAVIGATE_BACK_PRESSED_KEY
        )
        navController.navigateUp()
    }
}