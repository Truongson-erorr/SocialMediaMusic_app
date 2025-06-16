package com.example.musicapp_103.ui.theme.screen

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicapp_103.viewmodel.MoreViewModel
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun Detail_More(navController: NavController, moreId: String, moreViewModel: MoreViewModel = viewModel()) {
    val context = LocalContext.current
    val mores by moreViewModel.mores.collectAsState()
    val item = mores.find { it.id == moreId }

    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var duration by remember { mutableStateOf(0) }
    var currentPosition by remember { mutableStateOf(0) }

    var isFavorite by remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun playAudio() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(item?.audioUrl ?: "")
                    setOnPreparedListener {
                        start()
                        duration = it.duration
                        isPlaying = true
                    }
                    setOnErrorListener { _, what, extra ->
                        errorMessage = "Lỗi phát nhạc: $what, $extra"
                        true
                    }
                    prepareAsync()
                }
            } else {
                if (isPlaying) {
                    mediaPlayer?.pause()
                    isPlaying = false
                } else {
                    mediaPlayer?.start()
                    isPlaying = true
                }
            }
        } catch (e: Exception) {
            errorMessage = "Lỗi: ${e.message}"
        }
    }

    fun formatTime(ms: Int): String {
        val totalSecs = ms / 1000
        val mins = totalSecs / 60
        val secs = totalSecs % 60
        return String.format("%02d:%02d", mins, secs)
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = mediaPlayer?.currentPosition ?: 0
            duration = mediaPlayer?.duration ?: 0
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0033), Color(0xFF2E1A47))
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        if (item == null) {
            Text("Không tìm thấy bài hát", color = Color.White, fontSize = 20.sp)
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Quay lại",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                val infiniteTransition = rememberInfiniteTransition(label = "")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 20000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ), label = ""
                )
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = "Song Image",
                    modifier = Modifier
                        .size(300.dp)
                        .graphicsLayer(rotationZ = rotation)
                        .clip(CircleShape)
                        .shadow(16.dp, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.artist,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color(0xFFB0B0B0),
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(40.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = currentPosition.toFloat(),
                        onValueChange = { newValue ->
                            mediaPlayer?.seekTo(newValue.toInt())
                            currentPosition = newValue.toInt()
                        },
                        valueRange = 0f..(duration.toFloat()),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White,
                            inactiveTrackColor = Color.Gray
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = formatTime(currentPosition), color = Color.White, fontSize = 14.sp)
                        Text(text = formatTime(duration), color = Color.White, fontSize = 14.sp)
                    }
                }
                // Control buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /* TODO: Xử lý Previous */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = Color.White,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                    ) {
                        IconButton(
                            onClick = { playAudio() },
                            modifier = Modifier.size(70.dp)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.Black,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { /* TODO: Xử lý Next */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        if (isFavorite) {

                        } else {

                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    IconButton(onClick = {
                        // TODO: Handle download
                    }) {
                        Icon(
                            imageVector = Icons.Filled.FileDownload,
                            contentDescription = "Download",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    IconButton(onClick = {
                        // TODO: Handle add to album
                    }) {
                        Icon(
                            imageVector = Icons.Filled.LibraryAdd,
                            contentDescription = "Add to Album",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
