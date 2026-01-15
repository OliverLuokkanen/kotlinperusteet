package com.example.kotlinperusteet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kotlinperusteet.ui.theme.KotlinPerusteetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPerusteetTheme {
                HomeScreen() // poista tasks = mockTasks
            }
        }
    }
}