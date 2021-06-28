package com.example.bonfire.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bonfire.Constants.GOOGLE_SIGN_IN
import com.example.bonfire.MainActivity
import com.example.bonfire.R
import com.example.bonfire.data.User
import com.example.bonfire.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleSigninBtn.setOnClickListener {
            googleSignInClient.signInIntent.also {
                startActivityForResult(it, GOOGLE_SIGN_IN)
                authViewModel.enable.value = false
                authViewModel.progress.value = View.VISIBLE
            }
        }

        authViewModel.progress.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        authViewModel.enable.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.passwordEditText.isEnabled = it
            binding.loginBtn.isEnabled = it
            binding.emailInputLayout.isEnabled = it
            binding.passwordInputLayout.isEnabled = it
            binding.googleSigninBtn.isEnabled = it
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Email Cannot Be Empty"
            } else if (!isEmailValid(email)) {
                binding.emailInputLayout.error = "Email Is Not Valid"
            } else {
                binding.emailInputLayout.error = null
                if (password.isEmpty()) {
                    binding.passwordInputLayout.error = "Password Cannot Be Empty"
                } else if (password.length < 6) {
                    binding.passwordInputLayout.error =
                        "Password Should Be at-least 6 characters long"
                } else {
                    binding.passwordInputLayout.error = null
                    Log.i("Rohit Login", "Starting Main Activity")
                    authViewModel.signIn(email, password, requireActivity())
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                googleAuthForFirebase(account)
            }
            catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                authViewModel.enable.value = true
                authViewModel.progress.value = View.GONE
            }
        }
    }

    private fun googleAuthForFirebase(googleSignInAccount: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        Firebase.auth.signInWithCredential(credentials)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = Firebase.auth.currentUser!!
                    val user = User(
                        name = firebaseUser.displayName!!,
                        id = firebaseUser.uid,
                        imageUrl = firebaseUser.photoUrl!!.toString()
                    )

                    authViewModel.createUser(user, requireActivity())
                    updateUI(firebaseUser)
                }
                else {
                    Toast.makeText(requireContext(), "Error " + task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            authViewModel.progress.value = View.GONE
            authViewModel.updateLoginState()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun isEmailValid(text: String?): Boolean {
        return text != null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}