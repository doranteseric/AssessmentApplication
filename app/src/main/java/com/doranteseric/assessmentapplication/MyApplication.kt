package com.doranteseric.assessmentapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// MyApplication is the main application class, annotated with HiltAndroidApp to enable Hilt dependency injection.
@HiltAndroidApp
class MyApplication : Application() {
}
