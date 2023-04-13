package com.doranteseric.assessmentapplication

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


@Composable
fun ComposeVideoPlayer(
    uri: String,
    assetManager: AssetManager? = null,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean = true
) {
    val context = LocalContext.current
    val playerView = remember {
        PlayerView(context).apply {
            useController = true
        }
    }

    val exoPlayer = rememberExoPlayer(context)
    var prepared by remember { mutableStateOf(false) }

    LaunchedEffect(uri) {
        val mediaSource = if (assetManager != null) {
            val dataSourceFactory = DataSource.Factory {
                AssetDataSource(context).apply {
                    val assetUri = Uri.parse("file:///android_asset/$uri")
                    val dataSpec = DataSpec(assetUri)
                    open(dataSpec)
                }
            }
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("file:///android_asset/$uri"))
        } else {
            val dataSourceFactory = DefaultHttpDataSourceFactory("user-agent")
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(uri))
        }

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = playWhenReady
        prepared = true
    }

    AndroidView(
        factory = { playerView },
        modifier = modifier,
        update = {
            playerView.player = if (prepared) exoPlayer else null
        }
    )
}

@Composable
fun rememberExoPlayer(context: Context): ExoPlayer {
    return remember {
        SimpleExoPlayer.Builder(context).build().apply {
            playWhenReady = false
        }
    }
}
