package com.example.bonfire.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bonfire.R
import com.example.bonfire.databinding.FragmentRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment: Fragment(R.layout.fragment_room) {

    private var _binding : FragmentRoomBinding ?= null
    private val binding get() = _binding!!
    private val mref = FirebaseDatabase.getInstance().getReference("CurrentMeeting")
    private val mauth: FirebaseAuth=FirebaseAuth.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBinding.bind(view)
        binding.startcampLayout.visibility=View.GONE
        binding.joincampLay.visibility=View.GONE
        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("User1")) {

                    binding.joincampLay.visibility=View.VISIBLE
                    binding.startcampLayout.visibility=View.GONE
                    binding.meetingnamebyuser.setText(snapshot.child("User1").value.toString())
                    binding.statusofmeeting.visibility=View.GONE
                }
                else {
                    binding.joincampLay.visibility=View.GONE
                    binding.startcampLayout.visibility=View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })

        binding.joinOpenbtn.setOnClickListener{
            binding.joincampLay.visibility=View.VISIBLE
            binding.startcampLayout.visibility=View.GONE
            mref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("User1")) {

                        binding.cardView.visibility=View.VISIBLE
                        binding.statusofmeeting.visibility=View.GONE
                    }
                    else {
                        binding.statusofmeeting.visibility=View.VISIBLE
                        binding.meetingCv.visibility=View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.startopenBtn.setOnClickListener{
            binding.joincampLay.visibility=View.GONE
            binding.startcampLayout.visibility=View.VISIBLE


        }
        binding.createBtn.setOnClickListener {
            val intent = Intent(requireActivity(), VideoCallActivity::class.java)
            intent.putExtra("userName", "User1")
            mref.child("User1").setValue(mauth.currentUser?.displayName.toString());
            startActivity(intent)
        }
        binding.joinExMeeting.setOnClickListener {
            val intent = Intent(requireActivity(), VideoCallActivity::class.java)
            intent.putExtra("userName", "User2")
            mref.child("User2").setValue(mauth.currentUser?.displayName.toString());
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}