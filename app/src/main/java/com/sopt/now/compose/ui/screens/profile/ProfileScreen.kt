package com.sopt.now.compose.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.LOGIN_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.composables.TitleAndContentTextComposable
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.navigation.getDataFromPreviousBackStackEntry

private const val TAG = "ProfileScreen"



@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val user = navController.getDataFromPreviousBackStackEntry<User>(LOGIN_KEY)!!
    Log.d(TAG, "${user.value?.id}")
    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) {paddingValue ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(paddingValue)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        alignment = Alignment.CenterStart
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        alignment = Alignment.CenterStart
                    )
                }
                Text(
                    text = user.value!!.nickName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(5f)
                        .padding(start = 10.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.main_mbti_introduction, user.value!!.mbti),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )


            TitleAndContentTextComposable(
                title = R.string.title_pw,
                content = user.value!!.id,
                modifier = Modifier.padding(top = 70.dp)
            )
            TitleAndContentTextComposable(
                title = R.string.title_pw,
                content = user.value!!.pw,
                modifier = Modifier.padding(top = 70.dp)
            )
        }
    }
}