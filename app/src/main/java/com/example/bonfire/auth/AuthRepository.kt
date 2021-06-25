package com.example.bonfire.auth

import android.app.Activity
import com.example.bonfire.data.User
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseSource: AuthFirebaseSource) {

    val currentUser = firebaseSource.currentUser()
    val progress = firebaseSource.progress
    val enable = firebaseSource.enable
    val loginState = firebaseSource.loginState

    fun createUser(email: String, password: String, activity: Activity) =
        firebaseSource.createUserWithEmailAndPassword(email, password, activity)

    fun signIn(email: String, password: String, activity: Activity) =
        firebaseSource.signInWithEmailAndPassword(email, password, activity)

    fun createUser(user: User, activity: Activity) =
        firebaseSource.createUser(user, activity)

    fun updateLoginState() =
        firebaseSource.updateLoginState()

    fun logout() = firebaseSource.logout()

}