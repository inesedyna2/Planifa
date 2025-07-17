package com.example.cartedepresentation.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cartedepresentation.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDashboard(
    navController: NavController,
    viewModel: EmployeeDashboardViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val statutOptions = listOf("En attente", "En cours", "Terminée", "En difficulté")

    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF64B5F6)
    val backgroundColor = Brush.verticalGradient(listOf(primaryColor, secondaryColor))

    LaunchedEffect(Unit) {
        SessionManager.userId?.let { uid ->
            viewModel.currentUserId = uid
            viewModel.loadTasks()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column {
            // En-tête agent avec bouton déconnexion
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = primaryColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Bienvenue, ${SessionManager.nom ?: "Agent"}",
                                style = MaterialTheme.typography.titleMedium.copy(color = primaryColor)
                            )
                            Text(
                                text = "Sexe : ${SessionManager.sexe ?: "-"} | Département ID : ${SessionManager.departement ?: "-"}",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Bouton Déconnexion
                ElevatedButton(
                    onClick = {
                        SessionManager.clearSession()
                        navController.navigate("login") {
                            popUpTo("employee_dashboard") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Déconnexion", color = primaryColor)
                }
            }

            // Titre + indicateur nombre
            Text(
                text = "🗂️ Vos tâches assignées",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Nombre total : ${tasks.size}",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (tasks.isEmpty()) {
                Text("Aucune tâche assignée pour l'instant.", color = Color.White)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(tasks.size) { index ->
                        val task = tasks[index]
                        var selectedStatus by remember { mutableStateOf(task.statut) }
                        var expanded by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("📝 ${task.description}", fontWeight = FontWeight.Medium, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("📅 Du ${task.dateDebut} au ${task.dateFin}", color = Color.Gray)

                                Spacer(modifier = Modifier.height(8.dp))

                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }
                                ) {
                                    OutlinedTextField(
                                        value = selectedStatus,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Statut") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = primaryColor,
                                            unfocusedBorderColor = primaryColor,
                                            focusedLabelColor = primaryColor,
                                            unfocusedLabelColor = primaryColor
                                        )
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        statutOptions.forEach { status ->
                                            DropdownMenuItem(
                                                text = { Text(status) },
                                                onClick = {
                                                    selectedStatus = status
                                                    expanded = false
                                                    if (status != task.statut) {
                                                        viewModel.updateTaskStatus(task.id, status)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

