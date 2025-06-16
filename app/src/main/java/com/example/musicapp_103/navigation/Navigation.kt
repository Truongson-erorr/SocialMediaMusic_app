package com.example.musicapp_103.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.musicapp_103.ui.theme.screen.AddSingerScreen
import com.example.musicapp_103.ui.theme.screen.AddSongScreen
import com.example.musicapp_103.ui.theme.screen.AdminHomeScreen
import com.example.musicapp_103.ui.theme.screen.AdminScreen
import com.example.musicapp_103.ui.theme.screen.AdminSongScreen
import com.example.musicapp_103.ui.theme.screen.Detail_Feed
import com.example.musicapp_103.ui.theme.screen.Detail_More
import com.example.musicapp_103.ui.theme.screen.EditSingerScreen
import com.example.musicapp_103.ui.theme.screen.FeedScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.example.musicapp_103.ui.theme.screen.LoginScreen
import com.example.musicapp_103.ui.theme.screen.Splash_Screen
import com.example.musicapp_103.ui.theme.screen.Welcome
import com.example.musicapp_103.ui.theme.screen.Ho_me
import com.example.musicapp_103.ui.theme.screen.LibraryScreen
import com.example.musicapp_103.ui.theme.screen.LikedTracksScreen
import com.example.musicapp_103.ui.theme.screen.MoreListScreen
import com.example.musicapp_103.ui.theme.screen.PlaylistScreens
import com.example.musicapp_103.ui.theme.screen.RegisterScreen
import com.example.musicapp_103.ui.theme.screen.SingerDetailScreen
import com.example.musicapp_103.ui.theme.screen.SingerListScreen
import com.example.musicapp_103.ui.theme.screen.UserListScreen
import com.example.musicapp_103.viewmodel.FavViewModel
import com.example.musicapp_103.viewmodel.MoreViewModel
import com.example.musicapp_103.viewmodel.SongViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation_User() {
    val navController = rememberAnimatedNavController()
    val songViewModel: SongViewModel = viewModel()
    AnimatedNavHost(
        navController = navController,
        startDestination = "Splash_Screen",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = 600)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 600)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(durationMillis = 600)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, // Màn hình hiện tại trượt ra phải khi quay lại
                animationSpec = tween(durationMillis = 600)
            )
        }
    ) {
        composable("LoginScreen") { LoginScreen(navController) }
        composable("RegisterScreen") { RegisterScreen(navController) }
        composable("Splash_Screen") { Splash_Screen(navController) }
        composable("Welcome") { Welcome(navController) }
        composable("Ho_me") { Ho_me(navController) }
        composable("feed_screen") { FeedScreen(navController) }
        composable("LibraryScreen") { LibraryScreen(navController) }
        composable("liked_tracks") {
            LikedTracksScreen(navController = navController)
        }
        composable("playlists") {
            PlaylistScreens(navController = navController)
        }
        //man hinh admin quan tri
        composable("AdminHomeScreen") {
            AdminHomeScreen(navController)
        }
        composable("UserListScreen") {
            UserListScreen(navController)
        }
        composable("SingerListScreen") {
            SingerListScreen(navController)
        }
        composable("AdminSongScreen") {
            AdminSongScreen(navController)
        }
        composable("AddSingerScreen") {
            AddSingerScreen(navController)
        }
        composable("edit_singer/{singerId}") { backStackEntry ->
            val singerId = backStackEntry.arguments?.getString("singerId") ?: ""
            EditSingerScreen(navController, singerId)
        }
        composable("add_song") {
            AddSongScreen(navController)
        }
        composable("detail_feed/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: return@composable
            val songs by songViewModel.songs.collectAsState()

            val favViewModel: FavViewModel = viewModel()

            val currentIndex = songs.indexOfFirst { it.id == songId }
            if (currentIndex != -1) {
                Detail_Feed(
                    navController = navController,
                    songList = songs,
                    currentSongIndex = currentIndex,
                    favViewModel = favViewModel
                )
            } else {
                Text("Bài hát không tồn tại", color = Color.White, fontSize = 20.sp)
            }
        }
        composable("moreList") { MoreListScreen(navController = navController) }
        composable("detail_more/{moreId}") { backStackEntry ->
            val moreId = backStackEntry.arguments?.getString("moreId") ?: return@composable

            val moreViewModel: MoreViewModel = viewModel()
            val mores by moreViewModel.mores.collectAsState()

            val selectedItem = mores.find { it.id == moreId }

            if (selectedItem != null) {
                Detail_More(navController, moreId = moreId, moreViewModel = moreViewModel)
            } else {
                Text("Bài hát không tồn tại", color = Color.White, fontSize = 20.sp)
            }
        }
        composable("singer_detail/{singerId}/{singerName}/{singerImageUrl}") { backStackEntry ->
            val singerId = backStackEntry.arguments?.getString("singerId") ?: return@composable
            val singerName = backStackEntry.arguments?.getString("singerName") ?: "Ca sĩ"
            val singerImageUrl = backStackEntry.arguments?.getString("singerImageUrl") ?: ""

            SingerDetailScreen(
                navController = navController,
                singerId = singerId,
                singerName = singerName,
                singerImageUrl = singerImageUrl
            )
        }
        composable("AdminScreen") { AdminScreen(navController) }
    }
}