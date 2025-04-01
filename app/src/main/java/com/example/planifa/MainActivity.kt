package com.example.planifa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.planifa.navigation.Navigation
import com.example.planifa.ui.theme.PlanifaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanifaTheme {
                MaterialTheme {
                    Navigation()
                }
            }
        }
    }
}
