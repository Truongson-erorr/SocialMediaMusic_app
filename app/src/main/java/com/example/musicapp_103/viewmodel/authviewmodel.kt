package com.example.musicapp_103.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val _currentUser = mutableStateOf(FirebaseAuth.getInstance().currentUser)
    val currentUser: State<FirebaseUser?> = _currentUser

    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> = _userRole

    init {
        FirebaseAuth.getInstance().addAuthStateListener {
            _currentUser.value = it.currentUser
            fetchUserRole()
        }
    }

    private fun fetchUserRole() {
        val user = currentUser.value
        if (user != null) {
            FirebaseFirestore.getInstance().collection("Author")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    _userRole.value = document.getString("role")
                }
                .addOnFailureListener { }
        }
    }

    fun isLoggedIn(): Boolean = currentUser.value != null

}