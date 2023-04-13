package com.doranteseric.assessmentapplication

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@Composable
fun PictureScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.zIndex(1f)) {

            TopAppBar(
                title = { Text("Picture") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            ZoomableImage()
        }
    }

}