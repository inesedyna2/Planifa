package com.example.planifa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planifa.ui.components.AuthTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mot de passe",
            isPassword = true,
            leadingIcon = Icons.Default.Lock
        )

        Button(
            onClick = {
                // Simuler une connexion réussie et naviguer vers MainScreen
                navController.navigate("main")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }

        TextButton(onClick = { navController.navigate("signup") }) {
            Text("Créer un compte")
        }
    }
}
