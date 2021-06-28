package com.example.bonfire.auth

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bonfire.Constants.LOGIN_STATE_KEY
import com.example.bonfire.MainActivity
import com.example.bonfire.data.PreferencesManager
import com.example.bonfire.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFirebaseSource @Inject constructor(private val preferencesManager: PreferencesManager) {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val fireStore: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val userCollection = fireStore.collection("users")

    val progress = MutableLiveData(View.GONE)
    val enable = MutableLiveData(true)
    val loginState = preferencesManager.loginStateFlow

    fun createUserWithEmailAndPassword(email: String, password: String, activity: Activity) {
        progress.value = View.VISIBLE
        enable.value = false
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { createUser ->
                if (createUser.isSuccessful) {
                    firebaseAuth.currentUser!!.sendEmailVerification()
                        .addOnCompleteListener { emailVerification ->
                            if (emailVerification.isSuccessful) {
                                enable.value = true
                                progress.value = View.GONE
                                showToast(activity, "Email Verification Sent")
                            } else {
                                enable.value = true
                                progress.value = View.GONE
                                showToast(activity, emailVerification.exception?.message)
                            }
                        }
                } else {
                    enable.value = true
                    progress.value = View.GONE
                    showToast(activity, createUser.exception?.message)
                }
            }
            .addOnFailureListener {
                showToast(activity, it.message)
                enable.value = true
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String, activity: Activity) {
        progress.value = View.VISIBLE
        enable.value = false
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser!!.reload().addOnCompleteListener { reload ->
                        if (reload.isSuccessful) {
                            userCollection.document(firebaseAuth.currentUser!!.uid).get()
                                .addOnSuccessListener {
                                    if (it.exists()) {
                                        startMainActivity(activity)
                                    } else {
                                        if (firebaseAuth.currentUser?.isEmailVerified == true) {
                                            enable.value = true
                                            progress.value = View.GONE
                                            val user = User(
                                                id = firebaseAuth.currentUser!!.uid,
                                                name = firebaseAuth.currentUser!!.displayName.toString(),
                                                imageUrl = firebaseAuth.currentUser!!.photoUrl.toString()
                                            )
                                            createUser(user, activity)
                                            startMainActivity(activity)
                                            showToast(activity, "Main Activity")
                                        } else {
                                            enable.value = true
                                            progress.value = View.GONE
                                            showToast(
                                                activity,
                                                "Please verify your Email !!"
                                            )
                                        }
                                    }
                                }
                        } else {
                            enable.value = true
                            progress.value = View.GONE
                            showToast(activity, reload.exception?.message)
                        }
                    }
                } else {
                    enable.value = true
                    progress.value = View.GONE
                    showToast(activity, task.exception?.message)
                }
            }
            .addOnFailureListener {
                showToast(activity, it.message)
                enable.value = true
            }
    }

    fun createUser(user: User, activity: Activity) {
        userCollection.document(user.id).set(user).addOnCompleteListener { createUser ->
            if (createUser.isSuccessful) {
                showToast(activity, "User Created")
            }
            else {
                showToast(activity, createUser.exception?.message)
            }
        }
            .addOnFailureListener {
                showToast(activity, it.message)
            }
    }

    private fun startMainActivity(activity: Activity) {
        updateLoginState()
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun updateLoginState() {
        CoroutineScope(Dispatchers.IO).launch {
            preferencesManager.updateLoginState(
                LOGIN_STATE_KEY,
                true
            )
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    private fun showToast(activity: Activity, text: String?) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}