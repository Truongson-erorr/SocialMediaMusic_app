//package com.example.musicapp_103.ui.theme.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import androidx.compose.runtime.getValue
//// Data class đại diện cho một bài hát
//data class Song(
//    val id: String = "",
//    val title: String = "",
//    val artist: String = "",
//    val audioUrl: String = "",
//    val imageUrl: String = "",
//    val duration: Int = 0,
//    val singerId: String = ""
//)
//
//// ViewModel để lấy dữ liệu từ Firestore
//class SongViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val _songs = MutableStateFlow<List<Song>>(emptyList())
//    val songs: StateFlow<List<Song>> = _songs
//
//    init {
//        fetchSongs()
//    }
//
//    private fun fetchSongs() {
//        db.collection("song").addSnapshotListener { snapshot, e ->
//            if (e != null || snapshot == null) {
//                return@addSnapshotListener
//            }
//            _songs.value = snapshot.documents.mapNotNull { doc ->
//                try {
//                    Song(
//                        id = doc.id,
//                        title = doc.getString("title") ?: "",
//                        artist = doc.getString("artist") ?: "",
//                        audioUrl = doc.getString("audio_url") ?: "",
//                        imageUrl = doc.getString("image_url") ?: "",
//                        duration = doc.getLong("duration")?.toInt() ?: 0,
//                        singerId = doc.getString("singer_id") ?: ""
//                    )
//                } catch (e: Exception) {
//                    null
//                }
//            }
//        }
//    }
//}
//// Composable chính: Màn hình danh sách bài hát
//@Composable
//fun FeedScreen(viewModel: SongViewModel = viewModel()) {
//    val songs by viewModel.songs.collectAsState() // Lấy danh sách bài hát từ ViewModel
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black) // Màu nền tối sang trọng
//            .padding(16.dp),
//    ) {
//        Spacer(modifier = Modifier.height(40.dp))
//
//        // Tiêu đề bảng xếp hạng
//        Text(
//            text = "Bảng xếp hạng tuần",
//            style = TextStyle(fontSize = 26.sp, color = Color.White, fontWeight = FontWeight.Bold)
//        )
//        Spacer(modifier = Modifier.height(25.dp))
//
//        // Danh sách bài hát từ Firestore
//        LazyColumn {
//            items(songs) { song ->
//                SongItem(song = song, index = songs.indexOf(song) + 1) // Truyền bài hát và số thứ tự
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//        }
//    }
//}
//
//// Composable cho mỗi bài hát
//@Composable
//fun SongItem(song: Song, index: Int) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//            .clip(RoundedCornerShape(12.dp)), // Bo góc
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.Black), // Màu nền bài hát
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Hiệu ứng nổi
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF121212))
//                .padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Số thứ tự
//            Spacer(modifier = Modifier.width(16.dp))
//            // Ảnh bài hát từ image_url
//            AsyncImage(
//                model = song.imageUrl,
//                contentDescription = "Song Image",
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(RoundedCornerShape(8.dp)) // Bo góc ảnh
//                    .background(Color.Black) // Màu nền placeholder nếu ảnh không tải được
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//
//            // Thông tin bài hát
//            Column(
//                modifier = Modifier
//                    .weight(1f) // Chiếm toàn bộ không gian còn lại, đẩy IconButton sang phải
//            ) {
//                Text(
//                    text = song.title,
//                    color = Color.White,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = song.artist,
//                    color = Color.LightGray,
//                    fontSize = 14.sp
//                )
//            }
//            IconButton(
//                onClick = {
//
//                },
//                modifier = Modifier
//                    .padding(start = 8.dp) // Khoảng cách nhỏ để không sát mép quá
//            ) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,