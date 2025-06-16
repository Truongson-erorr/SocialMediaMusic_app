package com.example.musicapp_103.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicapp_103.data.model.Singers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SingersViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _singers = MutableStateFlow<List<Singers>>(emptyList())
    val singers: StateFlow<List<Singers>> = _singers

    init {
        fetchSingers()
    }

    private fun fetchSingers() {
        db.collection("Singers").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                return@addSnapshotListener
            }

            _singers.value = snapshot.documents.mapNotNull { doc ->
                try {
                    Singers(
                        name = doc.getString("name") ?: "",
                        image = doc.getString("image") ?: "",
                        id = doc.id,
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    fun addSinger(name: String, image: String) {
        val singerData = hashMapOf(
            "name" to name,
            "image" to image
        )
        db.collection("Singers").add(singerData)
    }

    fun updateSinger(id: String, name: String, image: String) {
        val singerData = hashMapOf(
            "name" to name,
            "image" to image
        )
        db.collection("Singers").document(id).set(singerData)
    }

    fun deleteSinger(id: String) {
        db.collection("Singers").document(id).delete()
    }
}



