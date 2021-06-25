package com.example.bonfire.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentRoomBinding

class RoomFragment: Fragment(R.layout.fragment_room) {

    private var _binding : FragmentRoomBinding ?= null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}