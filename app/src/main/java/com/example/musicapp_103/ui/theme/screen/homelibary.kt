package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun LibraryScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0033), Color(0xFF2E1A47))
                )
            )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Thư viện",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = { /* TODO: Navigate to settings */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                }
                IconButton(onClick = { /* TODO: Navigate to favorites */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorites", tint = Color.Red)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        val menuItems = listOf(
            "Bài hát đã yêu thích" to "liked_tracks",
            "Danh sách phát" to "playlists",
            "Albums của bạn" to "albums",
            "Ca sĩ đã theo dõi" to "following",
            "Bài hát đã tải xuống" to "stations"
        )
        LazyColumn {
            items(menuItems) { (item, route) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate(route) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item, color = Color.White, fontSize = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = ">", color = Color.Gray, fontSize = 23.sp)
                }
            }
        }
    }
}


