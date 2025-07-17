package com.example.planifa.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var tasks by remember { mutableStateOf(listOf<Task>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Gestion des Tâches", style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = { navController.navigate("addTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter une tâche")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tableau des tâches
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // En-tête du tableau
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(BorderStroke(1.dp, Color.Black))
                        .padding(4.dp)
                ) {
                    Text("Employé", modifier = Modifier.fillMaxWidth())
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(BorderStroke(1.dp, Color.Black))
                        .padding(4.dp)
                ) {
                    Text("Description", modifier = Modifier.fillMaxWidth())
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(BorderStroke(1.dp, Color.Black))
                        .padding(4.dp)
                ) {
                    Text("Actions", modifier = Modifier.fillMaxWidth())
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color.Black
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Color.Black
            )

            // Liste des tâches
            tasks.forEach { task ->
                TaskRow(task = task, onTaskChecked = { /* Mettre à jour l'état de la tâche */ })
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onTaskChecked: (Task) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(4.dp)
        ) {
            Text(task.employeeName, modifier = Modifier.fillMaxWidth())
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(4.dp)
        ) {
            Text(task.description, modifier = Modifier.fillMaxWidth())
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onTaskChecked(task.copy(isCompleted = it)) }
                )
                IconButton(onClick = { /* Modifier la tâche */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Modifier la tâche")
                }
                IconButton(onClick = { /* Supprimer la tâche */ }) {
                    Icon(Icons.Default.Delete, contentDescription = "Supprimer la tâche")
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
    }
}

data class Task(
    val id: Int,
    val employeeName: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val isCompleted: Boolean
)
