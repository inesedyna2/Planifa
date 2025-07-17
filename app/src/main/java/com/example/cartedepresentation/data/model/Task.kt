package com.example.cartedepresentation.data.model

data class Task(
    val id: String = "",
    val description: String = "",
    val statut: String = "En attente", // ou En cours / Terminée / En difficulté
    val dateDebut: String = "",
    val dateFin: String = "",
    val employeId: String = "", // clé étrangère vers user (employé)
    val departementId: String = "" // utile si tu veux savoir à quel département la tâche appartient
)
