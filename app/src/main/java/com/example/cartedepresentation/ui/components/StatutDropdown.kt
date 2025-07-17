package com.example.cartedepresentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatutDropdown(
    selectedStatut: String,
    onStatutSelected: (String) -> Unit,
    options: List<String> = listOf("En attente", "En cours", "Terminée", "En difficulté")
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedStatut,
            onValueChange = {},
            readOnly = true,
            label = { Text("Statut") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { statut ->
                DropdownMenuItem(
                    text = { Text(statut) },
                    onClick = {
                        onStatutSelected(statut)
                        expanded = false
                    }
                )
            }
        }
    }
}
