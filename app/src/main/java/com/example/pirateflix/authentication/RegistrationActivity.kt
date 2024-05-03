package com.example.pirateflix.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pirateflix.MainActivity
import com.example.pirateflix.R
import com.example.pirateflix.databinding.ActivityRegistrationBinding
import com.example.pirateflix.viewmodel.RegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import com.sg.magicpaybusiness.utils.AppConstants
import com.sg.magicpaybusiness.utils.AppPrefs

class RegistrationActivity : AppCompatActivity(),OnClickListener {
    lateinit var binding:ActivityRegistrationBinding
    lateinit var viewModel:RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.navigationBarColor = Color.BLACK

        viewModel=ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding.llSignUp.setOnClickListener(this)
        binding.rlSignUp.setOnClickListener(this)
        binding.pbReg.setMinFrame(10)
        binding.pbReg.setMaxFrame(50)


    }

    override fun onClick(v: View) {

        when(v) {
            binding.llSignUp->{
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
                this@RegistrationActivity.overridePendingTransition(
                    R.anim.swipe_right_enter,
                    R.anim.swipe_right_exit
                )
            }

            binding.rlSignUp->{
                if(binding.etEmail.text.toString().isNullOrBlank()){
                    binding.etEmail.requestFocus()
                    binding.etEmail.error="Please enter your email address"
                }
                else if(binding.etPassword.text.toString().length<6){
                    binding.etPassword.requestFocus()
                    binding.etPassword.error="Please enter your password"
                }
                else if(!binding.etConfirmPassword.text.toString().equals(binding.etPassword.text.toString())){
                    binding.etConfirmPassword.requestFocus()
                    binding.etConfirmPassword.error="Passwords Do Not Match"
                }
                else{
                    binding.pbReg.isVisible=true

                    registerUser()
                }
            }
        }
    }

    private fun registerUser() {
        viewModel.registerUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        observeRegisterOrNot()
    }

    fun observeRegisterOrNot(){
        viewModel.observeRegistrationResult().observe(this, Observer {result->
            if(result.status){
                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN,true,this)
                binding.pbReg.isVisible=false
                Snackbar.make(binding.layout,"User Registered Successfully",Snackbar.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                this@RegistrationActivity.overridePendingTransition(R.anim.swipe_right_enter,R.anim.swipe_right_exit)
            }
            else{
                Snackbar.make(binding.layout,result.message,Snackbar.LENGTH_LONG).show()
                binding.pbReg.isVisible=false

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LandingPageActivity::class.java))
        finish()
    }
}