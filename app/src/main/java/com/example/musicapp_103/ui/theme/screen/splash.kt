package com.example.musicapp_103.ui.theme.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.delay

@Composable
fun Splash_Screen(navController: NavController) {
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
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://res.cloudinary.com/dq64aidpx/image/upload/v1740565718/swea7tppxe4xzs28ehxq.png")
                .crossfade(true)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "Splash Screen Image"
        )
    }

    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("Welcome") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }
}
