
package com.example.musicapp_103.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp_103.data.model.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        db.collection("song")
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) {
                    return@addSnapshotListener
                }
                _songs.value = snapshot.documents.mapNotNull { doc ->
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
            }
    }

    fun addSong(title: String, artist: String, imageUrl: String, audioUrl: String, duration: Int, singerId: String) {
        val songData = hashMapOf(
            "title" to title,
            "artist" to artist,
            "image_url" to imageUrl,
            "audio_url" to audioUrl,
            "duration" to duration,
            "singer_id" to singerId
        )
        db.collection("song").add(songData)
    }
}