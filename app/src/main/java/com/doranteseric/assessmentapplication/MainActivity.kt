package com.doranteseric.assessmentapplication

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doranteseric.assessmentapplication.ui.theme.AssessmentApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json

// MainActivity is the main entry point of application.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Initializes the TriviaViewModel, which is responsible for managing the data and state of the UI.
    private val viewModel: TriviaViewModel by viewModels()

    // Sets up the UI and binds it to the viewModel.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssessmentApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "entrance") {
                        composable("entrance") { EntranceScreen(viewModel, navController) }
                        composable("trivia") { TriviaScreen(viewModel, navController) }
                        composable("question_detail?question_json={question_json}") { backStackEntry ->
                            val encodedJsonString = backStackEntry.arguments?.getString("question_json")
                            if (encodedJsonString != null) {
                                val jsonString = Uri.decode(encodedJsonString)
                                val json = Json { }
                                val triviaQuestion = json.decodeFromString(TriviaQuestion.serializer(), jsonString)
                                QuestionDetailScreen(triviaQuestion, navController)
                            } else {
                                // Handle the case when the jsonString is null
                            }
                        }
                        composable("contacts?question_json={question_json}") { backStackEntry ->
                            val encodedJsonString = backStackEntry.arguments?.getString("question_json")
                            if (encodedJsonString != null) {
                                val jsonString = Uri.decode(encodedJsonString)
                                val json = Json { }
                                val triviaQuestion = json.decodeFromString(TriviaQuestion.serializer(), jsonString)
                                ContactsScreen(triviaQuestion, navController)
                            } else {
                                // Handle the case when the jsonString is null
                            }
                        }
                        composable("watch_video") {
                            VideoScreen(navController)
                        }
                        composable("view_picture") {
                            PictureScreen(navController)
                        }
                    }

                    //TriviaScreen(viewModel)
                }

            }
        }
    }

}

