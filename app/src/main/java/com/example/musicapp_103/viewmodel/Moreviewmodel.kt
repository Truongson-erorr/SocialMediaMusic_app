package com.example.musicapp_103.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicapp_103.data.model.More
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _more = MutableStateFlow<List<More>>(emptyList())
    val mores: StateFlow<List<More>> = _more

    init {
        fetchMore()
    }

    private fun fetchMore() {
        db.collection("More").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                return@addSnapshotListener
            }
            _more.value = snapshot.documents.mapNotNull { doc ->
                try {
                    More(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        artist = doc.getString("artist") ?: "",
                        audioUrl = doc.getString("audio_url") ?: "",
                        imageUrl = doc.getString("image_url") ?: "",
                        genre = doc.getString("genre") ?: "",
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}
