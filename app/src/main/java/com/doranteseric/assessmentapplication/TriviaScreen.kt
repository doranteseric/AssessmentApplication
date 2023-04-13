package com.doranteseric.assessmentapplication


import android.animation.Animator
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.Global.putString
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import android.text.Html
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.gson.Gson
import androidx.navigation.NavOptionsBuilder
import com.google.gson.stream.JsonWriter
import kotlinx.serialization.json.Json
import java.io.StringWriter


// TriviaScreen is a composable function that displays the trivia questions fetched by the viewModel.
@Composable
fun TriviaScreen(viewModel: TriviaViewModel, navController: NavController) {
    val lazyPagingItems = viewModel.triviaQuestions.collectAsLazyPagingItems()
    val context = LocalContext.current
    val isAnimationPlayed = remember { mutableStateOf(false) }

    //for encoding
    // Create a StringWriter to hold the JSON output
    val writer = StringWriter()
    // Create a JsonWriter and set it to use the StringWriter
    val jsonWriter = JsonWriter(writer)


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = { Text("Trivia") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                elevation = 8.dp
            )

            if (isAnimationPlayed.value) {

                LazyColumn {
                    items(lazyPagingItems) { triviaQuestion ->
                        triviaQuestion?.let { question ->
                            QuestionItem(question) {
                                //println(Html.fromHtml(question.correct_answer).toString(),)

                                //Save this question locally
                                viewModel.saveQuestionToLocalDatabase(question)

                                val json = Json { }
                                val jsonString = json.encodeToString(TriviaQuestion.serializer(), question)
                                val encodedJsonString = Uri.encode(jsonString)
                                navController.navigate("question_detail?question_json=$encodedJsonString")

                            }
                        }
                    }

                    if (lazyPagingItems.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }


            }
        }

        if (!isAnimationPlayed.value || lazyPagingItems.loadState.refresh is LoadState.Loading) {
            AndroidView(
                factory = { context ->
                    LottieAnimationView(context).apply {
                        setAnimation(R.raw.loading)
                        if (!isAnimationPlayed.value) {
                            repeatCount = 0
                            addAnimatorListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {}
                                override fun onAnimationEnd(animation: Animator) {
                                    isAnimationPlayed.value = true
                                }
                                override fun onAnimationCancel(animation: Animator) {}
                                override fun onAnimationRepeat(animation: Animator) {}
                            })
                            playAnimation()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.8f))
            )
        }
    }
}

@Composable
fun QuestionItem(question: TriviaQuestion, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 0.dp
                )
                .sizeIn(
                    minWidth = 200.dp,
                    minHeight = 50.dp
                )
        ) {
            Text(
                text = Html.fromHtml(question.question).toString(),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}