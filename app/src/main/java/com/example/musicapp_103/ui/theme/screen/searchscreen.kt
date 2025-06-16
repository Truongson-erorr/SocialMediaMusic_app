package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp_103.viewmodel.SingersViewModel
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavController, singerViewModel: SingersViewModel = viewModel()) {
    val singersList by singerViewModel.singers.collectAsState()
    var searchText by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF2E2E2E))
                .border(1.dp, Color(0xFF6A6A6A), RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0xFFAAAAAA),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Nhập tên ca sĩ...",
                                color = Color(0xFF888888),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    cursorBrush = SolidColor(Color.White)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.star_on),
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ca sĩ bạn có thể thích",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(singersList.filter {
                it.name.contains(searchText, ignoreCase = true) || searchText.isEmpty()
            }) { singer ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            val encodedName = URLEncoder.encode(singer.name, StandardCharsets.UTF_8.toString())
                            val encodedImage = URLEncoder.encode(singer.image, StandardCharsets.UTF_8.toString())
                            navController.navigate("singer_detail/${singer.id}/$encodedName/$encodedImage")
                        }
                        .height(70.dp)
                ) {
                    AsyncImage(
                        model = singer.image,
                        contentDescription = "Singer Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xCC000000))
                                )
                            )
                            .padding(10.dp)
                    ) {
                        Text(
                            text = singer.name,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
