package com.example.cartedepresentation.ui.screens.department

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cartedepresentation.data.model.Department
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDepartmentScreen(
    navController: NavController,
    viewModel: AddDepartmentViewModel
) {
    val departmentToEdit = remember {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Department>("departmentToEdit")
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val departmentName by viewModel.departmentName
    val departmentDirecteur by viewModel.departmentDirecteur
    val directeurs by viewModel.directeurs
    val addDepartmentResult = viewModel.addDepartmentResult

    var directeurExpanded by remember { mutableStateOf(false) }

    // 📌 Charger les infos du département à l'arrivée
    LaunchedEffect(departmentToEdit) {
        departmentToEdit?.let { viewModel.loadDepartmentForEditing(it) }
    }

    // 📌 Gérer le résultat de l'enregistrement
    LaunchedEffect(addDepartmentResult.value) {
        addDepartmentResult.value?.let { result ->
            if (result.isSuccess) {
                scope.launch {
                    snackbarHostState.showSnackbar("Département mis à jour avec succès !")
                }
                viewModel.resetAddDepartmentResult()
                navController.popBackStack()
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Erreur : ${result.exceptionOrNull()?.message ?: "Inconnue"}"
                    )
                }
                viewModel.resetAddDepartmentResult()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("🔧 Modifier le département", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            departmentToEdit?.let {
                Text("Nom actuel : ${it.nom}")
                Text(
                    text = if (it.directeur.isBlank()) {
                        "Aucun directeur défini pour ce département."
                    } else {
                        "Directeur actuel : ${it.directeur}"
                    },
                    color = if (it.directeur.isBlank()) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = departmentName,
                onValueChange = viewModel::updateDepartmentName,
                label = { Text("Nom du département") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Choisir un nouveau directeur :", style = MaterialTheme.typography.bodyLarge)

            val selectedDirecteurName = directeurs.find { it.id == departmentDirecteur }?.nom
                ?: "Sélectionner un directeur"

            ExposedDropdownMenuBox(
                expanded = directeurExpanded,
                onExpandedChange = { directeurExpanded = !directeurExpanded }
            ) {
                OutlinedTextField(
                    value = selectedDirecteurName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Directeur") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)
                )
                ExposedDropdownMenu(
                    expanded = directeurExpanded,
                    onDismissRequest = { directeurExpanded = false }
                ) {
                    directeurs.forEach { user ->
                        println("Voici User : $user.nom")
                        DropdownMenuItem(
                            text = { Text(user.nom) },
                            onClick = {
                                viewModel.updateDepartmentDirecteur(user.id)
                                directeurExpanded = false
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.saveDepartment() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enregistrer les modifications")
            }
        }
    }
}
