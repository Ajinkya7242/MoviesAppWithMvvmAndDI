package com.example.pirateflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pirateflix.model.RegistrationStatusModel
import com.example.pirateflix.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.sg.magicpaybusiness.utils.AppPrefs

class RegistrationViewModel : ViewModel() {
    private var _registrationResult = MutableLiveData<RegistrationStatusModel>()
    private var _userModel = MutableLiveData<UserModel>()

    //    val registrationResultLiveData: LiveData<Boolean> = _registrationResult
    private val auth = FirebaseAuth.getInstance()


    fun registerUser(email: String, password:String){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->

            if(task.isSuccessful){
                _registrationResult.value=RegistrationStatusModel(true,"User Registred Successfully")
                _userModel.value=UserModel(auth.currentUser?.uid, email, password)
            }
            else{
                _registrationResult.value=RegistrationStatusModel(false,task.exception?.message.toString())
            }

        }
    }

    fun observeRegistrationResult():LiveData<RegistrationStatusModel>{
        return _registrationResult
    }

}