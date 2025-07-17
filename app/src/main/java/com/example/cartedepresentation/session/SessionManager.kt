package com.example.cartedepresentation.session

object SessionManager {
    var userId: String? = ""
    var email: String? = null
    var password: String? = null
    var nom: String? = null
    var role: String? = null
    var sexe: String? = null
    var departement: String? = ""

    fun clearSession() {
        userId = ""
        email = null
        password = null
        nom = null
        role = null
        sexe = null
        departement = ""
    }
}
