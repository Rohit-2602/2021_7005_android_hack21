package com.example.bonfire.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bonfire.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}