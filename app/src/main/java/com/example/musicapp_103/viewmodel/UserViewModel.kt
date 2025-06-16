package com.example.musicapp_103.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicapp_103.admin.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    fun fetchUsers() {
        db.collection("Author").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                return@addSnapshotListener
            }

            _userList.value = snapshot.documents.mapNotNull { doc ->
                try {
                    User(
                        id = doc.id,
                        email = doc.getString("email") ?: "",
                        role = doc.getString("role") ?: "user"
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    fun addUser(email: String, role: String) {
        val userData = hashMapOf(
            "email" to email,
            "role" to role
        )
        db.collection("Author").add(userData)
    }

    fun updateUser(id: String, newRole: String) {
        db.collection("Author").document(id).update("role", newRole)
    }

    fun deleteUser(id: String) {
        db.collection("Author").document(id).delete()
    }
}