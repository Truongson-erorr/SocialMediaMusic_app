package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AdminScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {

        // Ví dụ chức năng quản lý
        Button(onClick = { /* Xử lý logic quản lý người dùng */ }) {
            Text(text = "Manage Users")
        }
        Button(onClick = { /* Xử lý logic chỉnh sửa dữ liệu */ }) {
            Text(text = "Edit Data")
        }
        Button(onClick = { navController.navigate("logout") }) {
            Text(text = "Logout")
        }
    }
}