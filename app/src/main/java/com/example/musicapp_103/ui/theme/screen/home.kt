package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Ho_me(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Trang chủ", "Tìm Kiếm", "List", "Thư viện")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.List,
        Icons.Default.AccountCircle,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0033), Color(0xFF2E1A47))
                )
            )
    ) {
        Box(modifier = Modifier.weight(1f)) {
            when (selectedItem) {
                0 -> HomeScreen(navController)
                1 -> SearchScreen(navController)
                2 -> FeedScreen(navController)
                3 -> LibraryScreen(navController)
            }
        }
        NavigationBar(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF1A0033), // tím đậm gần như đen
                            Color(0xFF2E1A47)  // tím trung bình
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                )
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)), // Cắt phần ngoài cho bo tròn
            containerColor = Color.Transparent
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                            tint = if (selectedItem == index) Color.White else Color.LightGray,
                            modifier = Modifier.size(19.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item,
                            color = if (selectedItem == index) Color.White else Color.LightGray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }


    }
}



