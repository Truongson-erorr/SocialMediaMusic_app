package com.example.musicapp_103.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicapp_103.data.model.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchSongViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    fun getSongsBySingerId(singerId: String) {
        db.collection("song")
            .whereEqualTo("singer_id", singerId)
            .get()
            .addOnSuccessListener { result ->
                val filteredSongs = result.mapNotNull { doc ->
                    try {
                        Song(
                            id = doc.id,
                            title = doc.getString("title") ?: "",
                            artist = doc.getString("artist") ?: "",
                            audioUrl = doc.getString("audio_url") ?: "",
                            imageUrl = doc.getString("image_url") ?: "",
                            duration = doc.getLong("duration")?.toInt() ?: 0,
                            singerId = doc.getString("singer_id") ?: ""
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                _songs.value = filteredSongs
            }
    }
}