package com.jakir.cse24.personaldictionary.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signUp(user: User): MutableLiveData<LoginModel> {
        val signUp: MutableLiveData<LoginModel> = MutableLiveData()
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signUp.value = LoginModel(it.isSuccessful,"SignUp successful!")
                } else {
                    signUp.value = LoginModel(false,"SignUp failed!")
                }
            }.addOnCanceledListener {
                signUp.value = LoginModel(false,"Task $this was cancelled normally!")
            }.addOnFailureListener{
                signUp.value = LoginModel(false, it.message.toString())
                Log.e("SignUpRepository","OnFailureListener: ${it.message}")
                Log.e("SignUpRepository","OnFailureListener: ${it.localizedMessage}")
                Log.e("SignUpRepository","OnFailureListener: ${it.stackTrace}")
            }
        return signUp
    }
}