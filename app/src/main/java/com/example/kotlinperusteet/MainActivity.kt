package com.example.kotlinperusteet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.kotlinperusteet.ui.theme.KotlinPerusteetTheme
import com.example.kotlinperusteet.view.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPerusteetTheme {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}