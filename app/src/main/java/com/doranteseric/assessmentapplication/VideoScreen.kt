package com.doranteseric.assessmentapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun VideoScreen(navController: NavController) {
    Column {
        TopAppBar(
            title = { Text("Video") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        // Add your VideoScreen content here
        val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4" // Replace this with your video URL
        var isPlaying by remember { mutableStateOf(true) }

        ComposeVideoPlayer(
            uri = videoUrl,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isPlaying = !isPlaying
                },
            playWhenReady = isPlaying,
        )
    }
}