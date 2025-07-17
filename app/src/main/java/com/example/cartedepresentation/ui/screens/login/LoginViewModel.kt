package com.example.cartedepresentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.cartedepresentation.session.SessionManager

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> = _loginResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun onEmailChanged(new: String) {
        _email.value = new
    }

    fun onPasswordChanged(new: String) {
        _password.value = new
    }

    fun attemptLogin() {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value.trim()

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _loginResult.value = "Veuillez remplir tous les champs"
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            firestore.collection("users").document(userId)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        val role = document.getString("role")
                                        val nom = document.getString("nom")
                                        val departement = document.getString("departement")
                                        val user = auth.currentUser

                                        // Stockage dans la session
                                        SessionManager.userId = user?.uid
                                        SessionManager.email = user?.email
                                        SessionManager.role = role
                                        SessionManager.nom = nom
                                        SessionManager.departement = departement

                                        _loginResult.value = role ?: "Rôle inconnu"
                                    } else {
                                        _loginResult.value = "Aucune donnée utilisateur trouvée"
                                    }
                                    _isLoading.value = false
                                }
                                .addOnFailureListener { e ->
                                    _loginResult.value = "Erreur Firestore : ${e.localizedMessage}"
                                    _isLoading.value = false
                                }
                        } else {
                            _loginResult.value = "Utilisateur introuvable"
                            _isLoading.value = false
                        }
                    } else {
                        _loginResult.value = "Connexion échouée : ${task.exception?.localizedMessage}"
                        _isLoading.value = false
                    }
                }
        }
    }

    fun clearResult() {
        _loginResult.value = null
    }
}
