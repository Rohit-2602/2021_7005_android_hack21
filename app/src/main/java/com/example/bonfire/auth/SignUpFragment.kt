package com.example.bonfire.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentSignupBinding

class SignUpFragment: Fragment(R.layout.fragment_signup) {

    private var _binding : FragmentSignupBinding ?= null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}