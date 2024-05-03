package com.example.pirateflix.authentication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pirateflix.MainActivity
import com.example.pirateflix.R
import com.example.pirateflix.databinding.ActivityLoginBinding
import com.example.pirateflix.model.UserModel
import com.example.pirateflix.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.sg.magicpaybusiness.utils.AppConstants
import com.sg.magicpaybusiness.utils.AppPrefs

class LoginActivity : AppCompatActivity(), OnClickListener {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewBinding:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.navigationBarColor = Color.BLACK

        viewBinding= ViewModelProvider(this)[LoginViewModel::class.java]
        binding.pbLogin.setMinFrame(10)
        binding.pbLogin.setMaxFrame(50)
        binding.llSignUp.setOnClickListener(this)
        binding.rlLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.llSignUp -> {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
                this@LoginActivity.overridePendingTransition(
                    R.anim.swipe_right_enter,
                    R.anim.swipe_right_exit
                )
            }

           binding.rlLogin->{
               if(binding.etEmail.text.toString().isNullOrBlank()){
                   binding.etEmail.requestFocus()
                   binding.etEmail.error="Please enter your email address"
               }
               else if(binding.etPassword.text.toString().length<6){
                   binding.etPassword.requestFocus()
                   binding.etPassword.error="Please enter your password"
               }
               else{
                   loginUser()
                   binding.pbLogin.isVisible=true

               }
           }

        }
    }

    private fun loginUser() {
        viewBinding.loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        observeLoginStatus()
    }

    private fun observeLoginStatus() {
        viewBinding.observeLoginResult().observe(this,Observer{ result ->
            if(result.status){

                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN,true,this)
                binding.pbLogin.isVisible=false
                Snackbar.make(binding.layout,result.message, Snackbar.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                this@LoginActivity.overridePendingTransition(R.anim.swipe_right_enter,R.anim.swipe_right_exit)
            }
            else{
                binding.pbLogin.isVisible=false
                Snackbar.make(binding.layout,result.message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LandingPageActivity::class.java))
        finish()
    }
}