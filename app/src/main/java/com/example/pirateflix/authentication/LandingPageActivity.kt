package com.example.pirateflix.authentication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import com.example.pirateflix.R
import com.example.pirateflix.databinding.ActivityLandingpageBinding


class LandingPageActivity : AppCompatActivity(), OnClickListener {
    lateinit var binding: ActivityLandingpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding.rlSignUp.setOnClickListener(this)
        binding.rlLogin.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v) {
            binding.rlSignUp -> {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
                this@LandingPageActivity.overridePendingTransition(
                    R.anim.fade_enter,
                    R.anim.fade_exit
                )
            }

            binding.rlLogin -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                this@LandingPageActivity.overridePendingTransition(
                    R.anim.fade_enter,
                    R.anim.fade_exit
                )
            }
        }
    }
}