package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicapp_103.admin.view.SongViewModel
import com.example.musicapp_103.data.model.Song

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
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Quản lý bài hát",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

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
                        Text("Xem chi tiết", color = Color.White)
                    },
                    onClick = {
                        expanded = false
                        navController.navigate("detail_feed/${song.id}")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sửa bài hát", color = Color.White) },
                    onClick = {
                        expanded = false

                    }
                )
                DropdownMenuItem(
                    text = { Text("Xóa bài hát", color = Color.White) },
                    onClick = {
                        expanded = false

                    }
                )
            }
        }
    }
}