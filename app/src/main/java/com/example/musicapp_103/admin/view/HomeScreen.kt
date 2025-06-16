package com.example.musicapp_103.admin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
@Composable

fun AdminHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE0E0E0))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Trang Quản Trị",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminHomeButton("👤 Quản lý Người dùngg") {
                    navController.navigate("UserListScreen")
                }

                AdminHomeButton("🎵 Quản lý Bài hát") {
                    navController.navigate("FeedScreen")
                }

                AdminHomeButton("🎤 Quản lý Ca sĩ") {
                    // TODO: Thêm điều hướng
                }
            }
        }
    }
}

@Composable
fun AdminHomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black, // xanh dương đậm
            contentColor = Color.White
        )
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}
