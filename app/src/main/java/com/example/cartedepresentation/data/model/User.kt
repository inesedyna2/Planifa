package com.example.cartedepresentation.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val nom: String = "",
    val sexe: String = "",
    val role: String = "", // admin, directeur, agent
    val departement: String = "" // facultatif pour chef_entreprise
)
