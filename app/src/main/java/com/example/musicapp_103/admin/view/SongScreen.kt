package com.example.musicapp_103.admin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import com.example.musicapp_103.data.model.Song

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
}

@Composable
fun AdminSongScreen(navController: NavController, viewModel: SongViewModel = viewModel()) {
    val songs by viewModel.songs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE0E0E0)) // từ trắng đến xám nhạt
                )
            )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quản lý bài hát",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = {
                    navController.navigate("add_song")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Thêm bài hát", color = Color.White)
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("STT", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)
            Text("Ảnh", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
            Text("Tên bài hát", modifier = Modifier.weight(0.3f), fontWeight = FontWeight.Bold)
            Text("Ca sĩ", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
            Text("Tùy chọn", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
        }

        Divider()

        LazyColumn {
            itemsIndexed(songs) { index, song ->
                AdminSongRow(song = song, index = index + 1, navController)
                Divider()
            }
        }
    }
}

@Composable
fun AdminSongRow(song: Song, index: Int, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$index", modifier = Modifier.weight(0.1f))

        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .weight(0.2f)
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Text(song.title, modifier = Modifier.weight(0.3f))
        Text(song.artist, modifier = Modifier.weight(0.2f))

        Box(modifier = Modifier.weight(0.2f)) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.Black)
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Xem chi tiết", color = Color.White)},
                    onClick = {
                        expanded = false
                        navController.navigate("detail_feed/${song.id}")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sửa bài hát", color = Color.White) },
                    onClick = {
                        expanded = false
                        // TODO: Xử lý xóa
                    }
                )
                DropdownMenuItem(
                    text = { Text("Xóa bài hát", color = Color.White) },
                    onClick = {
                        expanded = false
                        // TODO: Xử lý xóa
                    }
                )
            }
        }
    }
}
