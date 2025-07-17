package com.example.cartedepresentation.ui.screens.agent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ListAgentScreen(navController: NavController) { // Récupérez l'adminId ici


    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) // Appliquer le padding du Scaffold
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Liste des agents", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}