package com.example.pirateflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pirateflix.model.LoginResultModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel :ViewModel(){

    private var loginResult=MutableLiveData<LoginResultModel>()
    private val auth:FirebaseAuth=FirebaseAuth.getInstance()

    fun loginUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{result ->

            if(result.isSuccessful){
                loginResult.value=LoginResultModel(true,"Login successful")
            }
            else{
                loginResult.value=LoginResultModel(false,result.exception?.message.toString())
            }
        }
    }

    fun observeLoginResult():LiveData<LoginResultModel>{
        return loginResult
    }

}