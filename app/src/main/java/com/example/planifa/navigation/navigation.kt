package com.example.planifa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planifa.ui.screens.LoginScreen
import com.example.planifa.ui.screens.SignUpScreen
import com.example.planifa.ui.screens.MainScreen
import com.example.planifa.ui.screens.AddTaskScreen

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("addTask") { AddTaskScreen(navController) }
    }
}
