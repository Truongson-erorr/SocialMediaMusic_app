package com.example.musicapp_103.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp_103.data.model.Playlist
import com.google.firebase.firestore.FirebaseFirestore

class PlaylistsViewmodel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> get() = _playlists

    init {
        fetchPlaylists()
    }

    private fun fetchPlaylists() {
        db.collection("Playlist")
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { doc ->
                    try {
                        Playlist(
                            song_id = doc.getString("song_id") ?: "",
                            title = doc.getString("title") ?: "",
                            artist = doc.getString("artist") ?: "",
                            image_url = doc.getString("image_url") ?: "",
                            audio_url = doc.getString("audio_url") ?: ""
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                _playlists.value = list
            }
            .addOnFailureListener { e ->
                println("Error fetching playlists: ${e.message}")
            }
    }

    fun addToPlaylist(playlist: Playlist) {
        db.collection("Playlist")
            .add(playlist)
            .addOnSuccessListener {
                println("Đã thêm vào playlist: ${playlist.title}")
                fetchPlaylists() // Cập nhật lại danh sách
            }
            .addOnFailureListener { e ->
                println("Lỗi khi thêm playlist: ${e.message}")
            }
    }
}
