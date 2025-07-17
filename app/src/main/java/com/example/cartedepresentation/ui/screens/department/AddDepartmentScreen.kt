package com.example.cartedepresentation.ui.screens.department

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun AddDepartmentScreen(
    navController: NavController,
    viewModel: AddDepartmentViewModel
) {
    val departmentName by viewModel.departmentName
    val addDepartmentResult = viewModel.addDepartmentResult

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF64B5F6)
    val backgroundBrush = Brush.verticalGradient(listOf(primaryColor, secondaryColor))

    LaunchedEffect(addDepartmentResult.value) {
        addDepartmentResult.value?.let { result ->
            if (result.isSuccess) {
                scope.launch {
                    snackbarHostState.showSnackbar("Département ajouté avec succès ✅")
                }
                viewModel.resetAddDepartmentResult()
                navController.popBackStack()
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("❗Erreur : ${result.exceptionOrNull()?.message}")
                }
                viewModel.resetAddDepartmentResult()
            }
        }
    }

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
                        "Ajouter un département",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = primaryColor,
                            fontSize = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ℹ️ Lors de l'ajout du département, on ne renseigne que son nom. Vous pourrez ajouter un directeur plus tard une fois que vous aurez ajouté des agents.",
                        textAlign = TextAlign.Justify,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    StyledTextField(
                        value = departmentName,
                        onValueChange = viewModel::updateDepartmentName,
                        label = "Nom du département"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.addDepartment() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✅ Ajouter le département", color = Color.White)
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
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
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
