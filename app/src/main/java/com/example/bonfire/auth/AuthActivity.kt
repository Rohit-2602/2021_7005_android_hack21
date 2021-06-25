package com.example.bonfire.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bonfire.MainActivity
import com.example.bonfire.R
import com.example.bonfire.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.enable.observe(this) {
            binding.tabLayout.isEnabled = it
        }

        val fragments = listOf(LoginFragment(), SignUpFragment())
        val viewPagerAdapter = ViewPagerAdapter(fragments, this)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Login In"
                1 -> tab.text = "Sign Up"
            }
        }.attach()

    }

    override fun onStart() {
        super.onStart()
        authViewModel.loginState.observe(this) { loginState ->
            if (loginState) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "User Not", Toast.LENGTH_SHORT).show()
                Log.i("Auth Activity Login", loginState.toString())
            }
        }
    }

}