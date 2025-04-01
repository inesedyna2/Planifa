package com.example.planifa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController) {
    var taskName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var selectedEmployee by remember { mutableStateOf("") }
    val employees = listOf("Employé 1", "Employé 2", "Employé 3") // Exemple de liste d'employés

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Nom de la tâche") }
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Date de début") }
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("Date de fin") }
        )

        Spacer(Modifier.height(16.dp))

        // Menu déroulant pour sélectionner un employé
        ExposedDropdownMenuBox(
            expanded = selectedEmployee.isNotEmpty(),
            onExpandedChange = { /* Pas besoin de gérer l'état ici */ }
        ) {
            OutlinedTextField(
                value = selectedEmployee,
                onValueChange = { selectedEmployee = it },
                readOnly = true,
                label = { Text("Sélectionner un employé") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = selectedEmployee.isNotEmpty()) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = selectedEmployee.isNotEmpty(),
                onDismissRequest = { selectedEmployee = "" }
            ) {
                employees.forEach { employee ->
                    DropdownMenuItem(
                        text = { Text(employee) },
                        onClick = {
                            selectedEmployee = employee
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // Ajouter la tâche à la base de données ou API
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter la tâche")
        }
    }
}
