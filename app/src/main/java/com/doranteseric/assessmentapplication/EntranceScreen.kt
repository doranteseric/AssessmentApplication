package com.doranteseric.assessmentapplication

import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieAnimationView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


/**
 * EntranceScreen composable displays a Lottie animation and a button to start the trivia game.
 * @param navController NavController to navigate between composable screens.
 */
@Composable
fun EntranceScreen(viewModel: TriviaViewModel, navController: NavController) {
    //
    val savedQuestions = viewModel.getSavedQuestions().observeAsState(emptyList())
    val lastQuestion = savedQuestions.value.lastOrNull()
    val animationResId = R.raw.welcome3



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Display the looping Lottie animation
        LoopingLottieAnimationView(
            animationResId = animationResId,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 32.dp),
            loop = true
        )

        // Display the button leading to the Trivia screen
        Button(
            onClick = { navController.navigate("trivia") },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("View Trivia")
        }

        if (lastQuestion != null) {
            println("WTH: " + lastQuestion)
            Text(
                text = "Last Question Attempted",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = Html.fromHtml(lastQuestion.question).toString(),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = Html.fromHtml(lastQuestion.correct_answer).toString(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(android.graphics.Color.parseColor("#0014FF"))
            )
        }
    }
}
