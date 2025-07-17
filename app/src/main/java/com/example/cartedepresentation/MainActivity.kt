package com.example.cartedepresentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cartedepresentation.navigation.AppNavGraph
import com.example.cartedepresentation.ui.screens.login.LoginViewModel
import com.example.cartedepresentation.ui.theme.CarteDePresentationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarteDePresentationTheme {
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel()

                AppNavGraph(
                    navController = navController,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}
