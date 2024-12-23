package dev.kichan.multiwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var firebaseAnalytics : FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        enableEdgeToEdge()
        setContent {
            MyApp(viewModel)
        }
    }
}