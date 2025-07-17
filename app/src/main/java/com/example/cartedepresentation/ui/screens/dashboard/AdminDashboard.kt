package com.example.cartedepresentation.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cartedepresentation.session.SessionManager
import com.example.cartedepresentation.ui.screens.dashboard.AdminDashboardViewModel


@Composable
fun AdminDashboard(
    navController: NavController,
    viewModel: AdminDashboardViewModel = viewModel()
) {
    val departmentsState = viewModel.departments.collectAsStateWithLifecycle()
    val allDepartments by departmentsState
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    val filteredDepartments = allDepartments.filter {
        it.nom.contains(searchQuery, ignoreCase = true)
    }

    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF64B5F6)
    val backgroundColor = Brush.verticalGradient(listOf(primaryColor, secondaryColor))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bonjour ${SessionManager.nom ?: "Chef d'entreprise"} 👨‍💼",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Bloc actions rapides
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Actions rapides",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = primaryColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ActionButton(
                        text = "➕ Ajouter un département",
                        onClick = { navController.navigate("add_department") },
                        backgroundColor = primaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ActionButton(
                        text = "👤 Ajouter un nouvel agent",
                        onClick = { navController.navigate("add_agent") },
                        backgroundColor = primaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ActionButton(
                        text = "📃 Voir la liste des agents",
                        onClick = { navController.navigate("list_agent") },
                        backgroundColor = primaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Titre + nombre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "📋 Liste des départements",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "(${filteredDepartments.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Barre de recherche avec icône loupe
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Rechercher...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher", tint = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                // CORRECTED: Removed focusedContainerColor and unfocusedContainerColor
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )

            )

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage != null) {
                Text(
                    text = "❗ $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (filteredDepartments.isEmpty()) {
                Text(
                    text = "Aucun département trouvé.",
                    color = Color.White
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    items(filteredDepartments) { dept ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("departmentToEdit", dept)
                                    navController.navigate("edit_department")
                                },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = dept.nom,
                                    style = MaterialTheme.typography.titleMedium.copy(color = primaryColor)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Directeur : ${dept.directeur}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ID : ${dept.id}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    padding: Dp = 12.dp
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.elevatedButtonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(padding)
        )
    }
}