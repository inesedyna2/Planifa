package com.example.cartedepresentation.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cartedepresentation.data.model.User

@Composable
fun DepartmentDashboard(
    navController: NavController,
    viewModel: DepartmentDashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val agents by viewModel.agents.collectAsState()

    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF64B5F6)

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(primaryColor, secondaryColor)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                "👔 Bonjour Directeur",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "📋 Liste des agents de votre département",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (agents.isEmpty()) {
                Text(
                    "Aucun agent n'est encore enregistré dans votre département.",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(agents.size) { index ->
                        val agent = agents[index]
                        AgentCard(agent = agent, onClick = {
                            navController.navigate("agentTasks/${agent.id}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AgentCard(agent: User, onClick: () -> Unit) {
    val primaryColor = Color(0xFF1976D2)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Agent",
                tint = primaryColor,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = agent.nom,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Sexe : ${agent.sexe}", color = Color.Gray)
                Text("Email : ${agent.email}", color = Color.Gray)
            }
        }
    }
}
