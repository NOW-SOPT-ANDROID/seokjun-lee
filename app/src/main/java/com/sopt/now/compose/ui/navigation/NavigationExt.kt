package com.sopt.now.compose.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController

/**
 * 이전 백스택 최상단 화면에 임시 저장된 데이터 불러오기
 */
fun <T> NavHostController.getDataFromPreviousBackStackEntry(
    key: String
): MutableLiveData<T>? = previousBackStackEntry?.savedStateHandle?.getLiveData(key)

/**
 * 현재 백스택 최상단 화면에 임시 저장된 데이터 불러오기
 */
fun <T> NavHostController.getDataFromCurrentBackStackEntry(
    key: String
): MutableLiveData<T>? = currentBackStackEntry?.savedStateHandle?.getLiveData(key)

/**
 * 이전 백스택 최상단 화면에 데이터 임시저장
 */
fun <T> NavHostController.putDataAtPreviousBackStackEntry(
    key: String,
    content: T
){
    previousBackStackEntry?.savedStateHandle?.set(key, content)
}

/**
 * 현재 백스택 최상단 화면에 데이터 임시저장
 */
fun <T> NavHostController.putDataAtCurrentStackEntry(
    key: String,
    content: T
){
    currentBackStackEntry?.savedStateHandle?.set(key, content)
}

