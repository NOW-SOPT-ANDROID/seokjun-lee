package com.sopt.now.compose.ext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.toList

/**
 * 이전 백스택 최상단 화면에 임시 저장된 데이터 불러오기
 */
fun <T> NavHostController.getDataFromPreviousBackStackEntry(
    key: String
): MutableLiveData<T>? = previousBackStackEntry?.savedStateHandle?.getLiveData(key)

/**
 * 이전 백스택 최상단 화면에 데이터 임시저장
 */
fun <T> NavHostController.putDataAtPreviousBackStackEntry(
    key: String,
    content: T
){
    previousBackStackEntry?.savedStateHandle?.set(key, content)
}

fun NavOptionsBuilder.removePreviousBackStacks(navController: NavHostController, screenInclusive: Boolean = true) {
    Log.d("HomeScreen", "before remove " +navController.previousBackStackEntry?.destination?.route!!)

    popUpTo(navController.currentBackStackEntry?.destination?.route!!)
    { inclusive = screenInclusive }
    Log.d("HomeScreen", "after remove "+navController.previousBackStackEntry?.destination?.route!!)
}

