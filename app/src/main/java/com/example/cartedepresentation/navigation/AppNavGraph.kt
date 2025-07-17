package com.example.cartedepresentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cartedepresentation.ui.screens.dashboard.AdminDashboard
import com.example.cartedepresentation.ui.screens.dashboard.DepartmentDashboard
import com.example.cartedepresentation.ui.screens.dashboard.EmployeeDashboard
import com.example.cartedepresentation.ui.screens.login.LoginScreen
import com.example.cartedepresentation.ui.screens.login.LoginViewModel
import com.example.cartedepresentation.ui.screens.department.AddDepartmentScreen
import com.example.cartedepresentation.ui.screens.department.AddDepartmentViewModel
import com.example.cartedepresentation.ui.screens.agent.ListAgentScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cartedepresentation.ui.screens.agent.AddAgentViewModel
import com.example.cartedepresentation.ui.screens.agent.AddAgentScreen
import com.example.cartedepresentation.ui.screens.department.EditDepartmentScreen
import com.example.cartedepresentation.ui.screens.task.AgentTaskScreen
import com.example.cartedepresentation.ui.screens.task.AgentTaskViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }

        composable("admin_dashboard") {
            AdminDashboard(navController = navController)
        }

        composable("department_dashboard") {
            DepartmentDashboard(navController = navController)
        }

        composable("employee_dashboard") {
            EmployeeDashboard(navController = navController)
        }

        composable("add_department") { backStackEntry ->
            val viewModel: AddDepartmentViewModel = viewModel(backStackEntry)
            AddDepartmentScreen(navController, viewModel)
        }

        composable("edit_department") { backStackEntry ->
            val viewModel: AddDepartmentViewModel = viewModel(backStackEntry)
            EditDepartmentScreen(navController, viewModel)
        }

        composable("add_agent") { backStackEntry ->
            val viewModel: AddAgentViewModel = viewModel(backStackEntry)
            AddAgentScreen(navController, viewModel)
        }

        composable("list_agent") {
            ListAgentScreen(navController = navController)
        }

        // Nouvelle route pour les tâches d'un agent
        composable(
            route = "agentTasks/{agentId}",
            arguments = listOf(navArgument("agentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val agentId = backStackEntry.arguments?.getString("agentId") ?: ""
            AgentTaskScreen(navController = navController, agentId = agentId)
        }
    }
}
