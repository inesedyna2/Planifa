package com.example.cartedepresentation.ui.screens.agent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAgentScreen(
    navController: NavController,
    viewModel: AddAgentViewModel
) {
    val agentName by viewModel.agentName
    val agentEmail by viewModel.agentEmail
    val agentPassword by viewModel.agentPassword
    val agentSexe by viewModel.agentSexe
    val agentDepartementId by viewModel.agentDepartement
    val departmentList by viewModel.departments

    val sexes = listOf("M", "F")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val addAgentResult = viewModel.addAgentResult

    LaunchedEffect(addAgentResult.value) {
        addAgentResult.value?.let { result ->
            if (result.isSuccess) {
                scope.launch {
                    snackbarHostState.showSnackbar("Agent ajouté avec succès ✅")
                }
                viewModel.resetAddAgentResult()
                navController.popBackStack()
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("❗Erreur : ${result.exceptionOrNull()?.message}")
                }
                viewModel.resetAddAgentResult()
            }
        }
    }

    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF64B5F6)
    val backgroundBrush = Brush.verticalGradient(listOf(primaryColor, secondaryColor))

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(paddingValues)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.TopCenter),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Ajouter un nouvel agent",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = primaryColor,
                            fontSize = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StyledTextField(
                        value = agentName,
                        onValueChange = viewModel::updateAgentName,
                        label = "Nom de l'agent"
                    )

                    StyledTextField(
                        value = agentEmail,
                        onValueChange = viewModel::updateAgentEmail,
                        label = "Email"
                    )

                    StyledTextField(
                        value = agentPassword,
                        onValueChange = viewModel::updateAgentPassword,
                        label = "Mot de passe",
                        isPassword = true
                    )

                    // Sexe Dropdown
                    var sexeExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = sexeExpanded,
                        onExpandedChange = { sexeExpanded = !sexeExpanded }
                    ) {
                        StyledTextField(
                            value = agentSexe,
                            onValueChange = {},
                            label = "Sexe",
                            readOnly = true,
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = sexeExpanded,
                            onDismissRequest = { sexeExpanded = false }
                        ) {
                            sexes.forEach { sexe ->
                                DropdownMenuItem(
                                    text = { Text(sexe) },
                                    onClick = {
                                        viewModel.updateAgentSexe(sexe)
                                        sexeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Département Dropdown
                    Spacer(modifier = Modifier.height(8.dp))
                    var deptExpanded by remember { mutableStateOf(false) }
                    val selectedDeptName = departmentList.find { it.id == agentDepartementId }?.nom
                        ?: "Sélectionner un département"

                    ExposedDropdownMenuBox(
                        expanded = deptExpanded,
                        onExpandedChange = { deptExpanded = !deptExpanded }
                    ) {
                        StyledTextField(
                            value = selectedDeptName,
                            onValueChange = {},
                            label = "Département",
                            readOnly = true,
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = deptExpanded,
                            onDismissRequest = { deptExpanded = false }
                        ) {
                            departmentList.forEach { dept ->
                                DropdownMenuItem(
                                    text = { Text(dept.nom) },
                                    onClick = {
                                        viewModel.updateAgentDepartement(dept.id)
                                        deptExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.addAgent() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✅ Ajouter l'agent", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isPassword: Boolean = false,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        readOnly = readOnly,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.padding(vertical = 6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
            focusedLabelColor = Color(0xFF1976D2),
            unfocusedLabelColor = Color(0xFF64B5F6),
            cursorColor = Color(0xFF1976D2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
