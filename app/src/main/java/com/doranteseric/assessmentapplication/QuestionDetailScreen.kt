package com.doranteseric.assessmentapplication

import android.net.Uri
import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.gson.Gson
import com.google.gson.stream.JsonWriter
import kotlinx.serialization.json.Json
import java.io.StringWriter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QuestionDetailScreen(question: TriviaQuestion, navController: NavController) {

    //
    val readContactsPermissionState = rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
    val shouldShowRationale = remember { mutableStateOf(false) }
    val animationResId = R.raw.hifive

    if (shouldShowRationale.value) {
        AlertDialog(
            onDismissRequest = { shouldShowRationale.value = false },
            title = { Text("Permission Denied") },
            text = { Text("You must grant access to contacts to proceed.") },
            confirmButton = {
                Button(onClick = {
                    shouldShowRationale.value = false
                    readContactsPermissionState.launchPermissionRequest()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { shouldShowRationale.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Congratulations") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            elevation = 8.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //TODO can add a randomize function so that the congratulations statement changes
            // Display the looping Lottie animation
            LoopingLottieAnimationView(
                animationResId = animationResId,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 32.dp),
                loop = true
            )

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = Html.fromHtml(question.question).toString(),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = Html.fromHtml(question.correct_answer).toString(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(android.graphics.Color.parseColor("#0014FF"))
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Check permission status and show button accordingly
            when {
                readContactsPermissionState.hasPermission -> {
                    Button(
                        onClick = {
                            val json = Json { }
                            val jsonString = json.encodeToString(TriviaQuestion.serializer(), question)
                            val encodedJsonString = Uri.encode(jsonString)
                            navController.navigate("contacts?question_json=$encodedJsonString")
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text("Share this Question!")
                    }
                }
                readContactsPermissionState.shouldShowRationale -> {
                    Button(
                        onClick = { shouldShowRationale.value = true },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text("Share this Question!")
                    }
                }
                else -> {
                    Button(
                        onClick = { readContactsPermissionState.launchPermissionRequest() },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text("Share this Question!")
                    }
                }
            }


            Text(
                text = "or try one of these next:",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("watch_video") },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Watch Video")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("view_picture") },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("View Picture")
            }

        }

    }

}