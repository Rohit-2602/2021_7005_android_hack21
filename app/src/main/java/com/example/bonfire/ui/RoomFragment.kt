package com.example.bonfire.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment: Fragment(R.layout.fragment_room) {

    private var _binding : FragmentRoomBinding ?= null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBinding.bind(view)

        binding.joinExMeeting.setOnClickListener {
            val intent = Intent(requireActivity(), VideoCallActivity::class.java)
            intent.putExtra("userName", "Rohit")
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}