package com.example.bonfire.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initUI()
        initOnlineRv();
    }

    private fun initOnlineRv() {

    }

    private fun initUI() {
        _binding?.rvOnlineUsers?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
