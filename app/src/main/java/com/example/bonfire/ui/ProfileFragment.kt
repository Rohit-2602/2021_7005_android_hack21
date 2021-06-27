package com.example.bonfire.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private var _binding : FragmentProfileBinding ?= null
    private val binding get() = _binding!!
    val mauth= FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        binding.profileName.setText(mauth.currentUser?.displayName.toString())
        Picasso.get().load(mauth.currentUser?.photoUrl).into(binding.profilePic)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}