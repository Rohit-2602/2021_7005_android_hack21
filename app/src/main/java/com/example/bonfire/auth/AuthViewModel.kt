package com.example.bonfire.auth

import android.app.Activity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bonfire.data.User
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val currentUser = authRepository.currentUser
    val progress = authRepository.progress
    val enable = authRepository.enable

    fun createUser(email: String, password: String, activity: Activity) =
        viewModelScope.launch {
            authRepository.createUser(email, password, activity)
        }

    fun signIn(email: String, password: String, activity: Activity) =
        viewModelScope.launch {
            authRepository.signIn(email, password, activity)
        }

    fun createUser(user: User, activity: Activity) =
        viewModelScope.launch {
            authRepository.createUser(user, activity)
        }

    fun updateLoginState() =
        authRepository.updateLoginState()

    val loginState = authRepository.loginState.asLiveData()

    fun logOut() = authRepository.logout()

}