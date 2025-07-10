package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicapp_103.data.model.Singers
import com.example.musicapp_103.viewmodel.SingersViewModel
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SingerListScreen(navController: NavController, viewModel: SingersViewModel = viewModel()) {
    val singers by viewModel.singers.collectAsState()

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
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Quản lý ca sĩ",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Button(
                onClick = {
                    navController.navigate("AddSingerScreen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Thêm ca sĩ", color = Color.White)
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
            Text("Tên ca sĩ", modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            Text("Tùy chọn", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
        }

        Divider()

        LazyColumn {
            itemsIndexed(singers) { index, singer ->
                AdminSingerRow(singer = singer, index = index + 1, navController, viewModel)
                Divider()
            }
        }
    }
}

@Composable
fun AdminSingerRow(singer: Singers, index: Int, navController: NavController, viewModel: SingersViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa ca sĩ này không?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteSinger(singer.id)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Xóa", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text("Hủy", color = Color.Black)
                }
            }
        )
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$index", modifier = Modifier.weight(0.1f))

        AsyncImage(
            model = singer.image,
            contentDescription = null,
            modifier = Modifier
                .weight(0.2f)
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Text(singer.name, modifier = Modifier.weight(0.5f))

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
                    text = { Text("Sửa ca sĩ", color = Color.White) },
                    onClick = {
                        expanded = false
                        navController.navigate("edit_singer/${singer.id}")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Xóa ca sĩ", color = Color.White) },
                    onClick = {
                        expanded = false
                        showDialog = true
                    }
                )
            }
        }
    }
}
