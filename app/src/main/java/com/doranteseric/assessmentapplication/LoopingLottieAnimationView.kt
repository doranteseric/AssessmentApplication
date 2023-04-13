package com.doranteseric.assessmentapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

/**
 * LoopingLottieAnimationView composable displays a Lottie animation view that can loop.
 * @param animationResId Resource ID of the Lottie animation JSON file.
 * @param modifier Modifier to apply to the Lottie animation view.
 * @param loop If true, the Lottie animation will loop indefinitely.
 */
@Composable
fun LoopingLottieAnimationView(
    animationResId: Int,
    modifier: Modifier = Modifier,
    loop: Boolean = false
) {
    val context = LocalContext.current

    // Use AndroidView to create and update the LottieAnimationView
    AndroidView(
        factory = {
            // Initialize the LottieAnimationView with the given context
            LottieAnimationView(context).apply {
                // Set the animation resource
                setAnimation(animationResId)

                // Enable looping if specified
                if (loop) {
                    repeatCount = LottieDrawable.INFINITE
                    playAnimation()
                }
            }
        },
        modifier = modifier
    )
}

