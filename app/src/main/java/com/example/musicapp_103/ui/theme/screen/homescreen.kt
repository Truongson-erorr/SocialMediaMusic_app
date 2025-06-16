package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp_103.data.model.More
import com.example.musicapp_103.viewmodel.MoreViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController, viewModel: MoreViewModel = viewModel()) {
    val moreList by viewModel.mores.collectAsState()

    val bannerImages = listOf(
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1744719177/a7kcniebpn2saimioeko.jpg",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1744719407/dd4rfmsyp5xze88x81k2.jpg",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1744731955/txqj3t1tps93osooynsj.webp",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1744723018/fyxdeld1dlvi67lpjng6.jpg"
    )

    var currentBannerIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentBannerIndex = (currentBannerIndex + 1) % bannerImages.size
        }
    }

    val currentUser by rememberUpdatedState(FirebaseAuth.getInstance().currentUser)
    val userName = currentUser?.displayName ?: currentUser?.email ?: "Người dùng"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = if (currentUser != null) "Chào mừng bạn, $userName!" else "Chào mừng, bạn chưa đăng nhập!",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                Box {
                    AsyncImage(
                        model = bannerImages[currentBannerIndex],
                        contentDescription = "Banner Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xAA000000))
                                )
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nhịp điệu cuộc sống, là do bạn!",
                            color = Color.LightGray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            SectionHeader(title = "Top bài hát 2024") {
                navController.navigate("moreList")
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(moreList) { item ->
                    MusicItem(item = item) {
                        navController.navigate("detail_more/${item.id}")
                    }
                }
            }
        }

        item {
            SectionHeader(title = "Ca sĩ xu hướng") {
                navController.navigate("moreList")
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(moreList) { item ->
                    MusicItem(item = item) {
                        navController.navigate("detail_more/${item.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onClickViewAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Xem tất cả",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.clickable { onClickViewAll() }
        )
    }
}

@Composable
fun MusicItem(item: More, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Text(
            text = item.artist,
            color = Color.Gray,
            fontSize = 13.sp,
            maxLines = 1
        )
    }
}
