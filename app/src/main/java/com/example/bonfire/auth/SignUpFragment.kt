package com.example.bonfire.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment(R.layout.fragment_signup) {

    private var _binding : FragmentSignupBinding ?= null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupBinding.bind(view)

        authViewModel.progress.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        authViewModel.enable.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.passwordEditText.isEnabled = it
            binding.passwordCheckEditText.isEnabled = it
            binding.emailInputLayout.isEnabled = it
            binding.passwordInputLayout.isEnabled = it
            binding.passwordCheckInputLayout.isEnabled = it
            binding.signupBtn.isEnabled = it
        }

        binding.signupBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordCheck = binding.passwordCheckEditText.text.toString()

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
                    if (password == passwordCheck) {
                        binding.passwordInputLayout.error = null
                        authViewModel.createUser(email, password, requireActivity())
                    } else {
                        binding.passwordCheckInputLayout.error = "Password Doesn't Match"
                    }
                }
            }
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