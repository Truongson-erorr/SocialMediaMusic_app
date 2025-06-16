package com.example.musicapp_103.ui.theme.screen

import androidx.compose.runtime.Composable
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.graphics.Brush
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController) {
    val auth = remember { FirebaseAuth.getInstance() }
    val context = LocalContext.current
    val firestore = remember { FirebaseFirestore.getInstance() } // Thêm Firestore instance

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A0033), Color(0xFF2E1A47))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Text(
                text = "Đăng Ký",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B0082)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Gray, fontSize = 10.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF4B0082),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF4B0082)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu", color = Color.Gray, fontSize = 10.sp) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF4B0082),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF4B0082)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    isLoading = true
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                if (user != null) {
                                    // Lưu dữ liệu vào Firestore collection "authors"
                                    val userData = hashMapOf(
                                        "email" to email,
                                        "role" to "user", // Mặc định là user, có thể thay đổi sau
                                        "createdAt" to System.currentTimeMillis()
                                    )
                                    firestore.collection("Author")
                                        .document(user.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Đăng ký và lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Lỗi khi lưu dữ liệu: ${it.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4B0082),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("ĐĂNG KÝ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("LoginScreen") }) {
                Text("Đã có tài khoản? Đăng nhập ngay", color = Color(0xFF4B0082), fontSize = 10.sp)
            }
        }
    }
}