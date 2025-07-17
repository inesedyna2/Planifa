package com.example.cartedepresentation.data.model

data class Employe(
    val id: String = "",
    val userId: String = "",       // clé étrangère vers un user
    val poste: String = "",
    val departementId: String = "" // clé étrangère vers un département
)
