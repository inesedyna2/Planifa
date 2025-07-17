package com.example.cartedepresentation.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.User
import com.example.cartedepresentation.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.cartedepresentation.session.SessionManager

class DepartmentDashboardViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _agents = MutableStateFlow<List<User>>(emptyList())
    val agents: StateFlow<List<User>> = _agents

    var currentDepartmentId: String? = null // Changed to nullable String

    init {
        currentDepartmentId = SessionManager.departement
        loadAgents()
    }

    fun loadAgents() {
        viewModelScope.launch {
            // Use 'let' to execute the block only if currentDepartmentId is not null
            currentDepartmentId?.let { departmentId ->
                val result = userRepository.getUsersByDepartment(departmentId) // departmentId is now guaranteed non-null
                result.onSuccess {
                    _agents.value = it.filter { user -> user.role == "agent" }
                }.onFailure {
                    println("Erreur lors du chargement des agents : ${it.message}")
                }
            } ?: run {
                // This block executes if currentDepartmentId is null
                println("Erreur: Le département n'est pas défini. Impossible de charger les agents.")
                // You might want to update the UI or log a user-friendly message here
            }
        }
    }
}
