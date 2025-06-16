package com.example.musicapp_103.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp_103.data.model.FavoriteSong
import com.example.musicapp_103.data.model.Song
import com.google.firebase.firestore.FirebaseFirestore

class FavViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _favoriteSongs = MutableLiveData<List<FavoriteSong>>()
    val favoriteSongs: LiveData<List<FavoriteSong>> get() = _favoriteSongs

    init {
        fetchFavoriteSongs()
    }

    private fun fetchFavoriteSongs() {
        db.collection("FavoriteSong")
            .get()
            .addOnSuccessListener { result ->
                val songs = result.mapNotNull { it.toObject(FavoriteSong::class.java) }
                _favoriteSongs.value = songs
            }
            .addOnFailureListener { e ->
                println("Error fetching favorite songs: ${e.message}")
            }
    }

    fun addToFavorites(song: Song) {
        val favSong = FavoriteSong(
            song_id = song.id,
            title = song.title,
            artist = song.artist,
            image_url = song.imageUrl,
            audio_url = song.audioUrl
        )

        db.collection("FavoriteSong")
            .add(favSong)
            .addOnSuccessListener {
                println("Added to favorites: ${favSong.title}")
            }
            .addOnFailureListener { e ->
                println("Error adding to favorites: ${e.message}")
            }
    }
    fun removeFromFavorites(song: FavoriteSong) {
        db.collection("FavoriteSong") // Đảm bảo giống với tên collection bạn dùng khi thêm
            .whereEqualTo("song_id", song.song_id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("FavoriteSong").document(document.id).delete()
                }
                fetchFavoriteSongs() // Cập nhật lại danh sách sau khi xóa
            }
            .addOnFailureListener { e ->
                println("Error removing favorite: ${e.message}")
            }
    }

}
