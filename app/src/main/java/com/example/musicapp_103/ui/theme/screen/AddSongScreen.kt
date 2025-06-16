package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicapp_103.viewmodel.SongViewModel

@Composable
fun AddSongScreen(navController: NavController, viewModel: SongViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var audioUrl by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var singerId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE0E0E0))
                )
            )
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(70.dp))
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
                text = "Thêm bài hát",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tên bài hát") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it },
            label = { Text("Ca sĩ") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL ảnh") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = audioUrl,
            onValueChange = { audioUrl = it },
            label = { Text("URL audio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Thời lượng (giây)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = singerId,
            onValueChange = { singerId = it },
            label = { Text("ID ca sĩ") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && artist.isNotBlank() && imageUrl.isNotBlank() &&
                    audioUrl.isNotBlank() && duration.isNotBlank() && singerId.isNotBlank()
                ) {
                    val durationInt = duration.toIntOrNull() ?: 0
                    viewModel.addSong(title, artist, imageUrl, audioUrl, durationInt, singerId)
                    navController.navigateUp()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Thêm", color = Color.White)
        }
    }
}