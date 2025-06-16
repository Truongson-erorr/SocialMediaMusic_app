package com.example.musicapp_103.ui.theme.screen

import android.app.DownloadManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicapp_103.data.model.Playlist
import com.example.musicapp_103.data.model.Song
import com.example.musicapp_103.viewmodel.FavViewModel
import com.example.musicapp_103.viewmodel.PlaylistsViewmodel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun Detail_Feed(navController: NavController, songList: List<Song>, currentSongIndex: Int, favViewModel: FavViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val scaleFactor = screenWidth / 360f

    fun scaled(dp: Int) = (dp * scaleFactor).dp
    fun scaledSp(sp: Int) = (sp * scaleFactor).sp

    var currentIndex by remember { mutableStateOf(currentSongIndex) }
    val currentSong = remember(currentIndex) { songList[currentIndex] }

    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var currentPosition by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(0) }

    var isFavorite by remember { mutableStateOf(false) }
    val playlistViewModel: PlaylistsViewmodel = viewModel()
    val playlists by playlistViewModel.playlists.observeAsState(emptyList())
    var isInPlaylist by remember(currentSong.id) {
        mutableStateOf(playlists.any { it.song_id == currentSong.id })
    }
    val auth = FirebaseAuth.getInstance()

    DisposableEffect(currentSong.audioUrl) {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        currentPosition = 0
        duration = 0
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun startMediaPlayer(song: Song) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(song.audioUrl)
                setOnPreparedListener {
                    duration = it.duration
                    it.start()
                    isPlaying = true
                }
                setOnErrorListener { _, what, extra ->
                    errorMessage = "Lỗi phát nhạc: $what, $extra"
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            errorMessage = "Lỗi phát nhạc: ${e.message}"
        }
    }

    fun togglePlayback() {
        mediaPlayer?.let {
            if (isPlaying) {
                it.pause()
                isPlaying = false
            } else {
                it.start()
                isPlaying = true
            }
        } ?: startMediaPlayer(currentSong)
    }

    fun playRandomSong() {
        val availableIndices = songList.indices.filter { it != currentIndex }
        if (availableIndices.isNotEmpty()) {
            val newIndex = availableIndices.random()
            currentIndex = newIndex
        }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            mediaPlayer?.let {
                currentPosition = it.currentPosition
                duration = it.duration
            }
            delay(500)
        }
    }

    fun formatTime(millis: Int): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0033), Color(0xFF2E1A47))
                )
            )
            .padding(horizontal = scaled(20), vertical = scaled(24))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(scaled(30)))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(scaled(28))
                    )
                }
                Spacer(modifier = Modifier.width(scaled(8)))
                Text(
                    text = "Quay lại",
                    color = Color.White,
                    fontSize = scaledSp(18),
                    fontWeight = FontWeight.SemiBold
                )
            }

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
                model = currentSong.imageUrl,
                contentDescription = "Song Image",
                modifier = Modifier
                    .size(scaled(250))
                    .graphicsLayer(rotationZ = rotation)
                    .clip(CircleShape)
                    .shadow(scaled(16), CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(scaled(22)))

            Text(
                text = currentSong.title,
                style = TextStyle(fontSize = scaledSp(16), fontWeight = FontWeight.ExtraBold, color = Color.White)
            )

            Text(
                text = currentSong.artist,
                style = TextStyle(fontSize = scaledSp(14), fontWeight = FontWeight.Medium, color = Color(0xFFB0B0B0))
            )

            Spacer(modifier = Modifier.height(scaled(20)))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Slider(
                    value = currentPosition.toFloat(),
                    onValueChange = {
                        mediaPlayer?.seekTo(it.toInt())
                        currentPosition = it.toInt()
                    },
                    valueRange = 0f..duration.toFloat(),
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
                    Text(formatTime(currentPosition), color = Color.White, fontSize = scaledSp(10))
                    Text(formatTime(duration), color = Color.White, fontSize = scaledSp(10))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { playRandomSong() }) {
                    Icon(Icons.Filled.SkipPrevious, "Previous", tint = Color.White, modifier = Modifier.size(scaled(30)))
                }

                Spacer(modifier = Modifier.width(scaled(32)))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(scaled(80))
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                ) {
                    IconButton(onClick = { togglePlayback() }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.Black,
                            modifier = Modifier.size(scaled(28))
                        )
                    }
                }

                Spacer(modifier = Modifier.width(scaled(32)))

                IconButton(onClick = { playRandomSong() }) {
                    Icon(Icons.Filled.SkipNext, "Next", tint = Color.White, modifier = Modifier.size(scaled(30)))
                }
            }

            Spacer(modifier = Modifier.height(scaled(24)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            favViewModel.addToFavorites(currentSong)
                            Toast.makeText(context, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng đăng nhập để sử dụng chức năng này!", Toast.LENGTH_SHORT).show()
                        navController.navigate("LoginScreen")
                    }
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White,
                        modifier = Modifier.size(scaled(22))
                    )
                }

                IconButton(onClick = {
                    Toast.makeText(context, "Tải xuống thành công!", Toast.LENGTH_SHORT).show()
                    val request = DownloadManager.Request(Uri.parse(currentSong.audioUrl)).apply {
                        setTitle("Tải xuống: ${currentSong.title}")
                        setDescription("Đang tải về bài hát...")
                        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "${currentSong.title}.mp3")
                        setAllowedOverMetered(true)
                        setAllowedOverRoaming(true)
                    }
                    val downloadManager = context.getSystemService(DownloadManager::class.java)
                    downloadManager.enqueue(request)
                }) {
                    Icon(Icons.Filled.FileDownload, "Download", tint = Color.White, modifier = Modifier.size(scaled(22)))
                }

                IconButton(onClick = {
                    Toast.makeText(context, "Đã thêm vào danh sách phát!", Toast.LENGTH_SHORT).show()
                    isInPlaylist = !isInPlaylist
                    val playlist = Playlist(
                        song_id = currentSong.id,
                        title = currentSong.title,
                        artist = currentSong.artist,
                        image_url = currentSong.imageUrl,
                        audio_url = currentSong.audioUrl
                    )
                    if (isInPlaylist) playlistViewModel.addToPlaylist(playlist)
                }) {
                    Icon(
                        imageVector = if (isInPlaylist) Icons.Filled.LibraryAddCheck else Icons.Filled.LibraryAdd,
                        contentDescription = "Add to Playlist",
                        tint = if (isInPlaylist) Color.Green else Color.White,
                        modifier = Modifier.size(scaled(22))
                    )
                }
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(scaled(14)))
                Text(text = it, color = Color.Red, fontSize = scaledSp(14))
            }
        }
    }
}
