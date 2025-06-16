package com.example.musicapp_103.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicapp_103.viewmodel.UserViewModel

@Composable
fun UserListScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val userList by userViewModel.userList.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<Pair<String, String>?>(null) }
    var showDeleteDialog by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        userViewModel.fetchUsers()
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(66.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                    fontWeight = FontWeight.Bold,
                    text = "Danh sách",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Thêm người dùng")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(userList) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Email: ${user.email}", fontWeight = FontWeight.Bold)
                        Text("Vai trò: ${user.role}", fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    showEditDialog = user.id to user.role
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Sửa")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    showDeleteDialog = user.id
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Xóa")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        UserDialog(
            title = "Thêm người dùng",
            onConfirm = { email, role ->
                userViewModel.addUser(email, role)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    showEditDialog?.let { (id, currentRole) ->
        UserDialog(
            title = "Sửa vai trò",
            initialEmail = "",
            initialRole = currentRole,
            editableEmail = false,
            onConfirm = { _, newRole ->
                userViewModel.updateUser(id, newRole)
                showEditDialog = null
            },
            onDismiss = { showEditDialog = null }
        )
    }

    showDeleteDialog?.let { userId ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa người dùng này không?") },
            confirmButton = {
                Button(
                    onClick = {
                        userViewModel.deleteUser(userId)
                        showDeleteDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun UserDialog(
    title: String,
    initialEmail: String = "",
    initialRole: String = "user",
    editableEmail: Boolean = true,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf(initialEmail) }
    var role by remember { mutableStateOf(initialRole) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onConfirm(email, role) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        },
        title = { Text(title) },
        text = {
            Column {
                if (editableEmail) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Vai trò (user/admin)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}