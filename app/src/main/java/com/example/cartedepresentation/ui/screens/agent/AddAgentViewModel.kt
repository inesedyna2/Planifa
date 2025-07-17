package com.example.cartedepresentation.ui.screens.agent

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.Department
import com.example.cartedepresentation.data.model.User
import com.example.cartedepresentation.data.repository.DepartmentRepository
import com.example.cartedepresentation.data.repository.UserRepository
import kotlinx.coroutines.launch

class AddAgentViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val departmentRepository: DepartmentRepository = DepartmentRepository()
) : ViewModel() {

    init {
        loadDepartments()
    }

    // === Départements ===
    private val _departments = mutableStateOf<List<Department>>(emptyList())
    val departments: State<List<Department>> = _departments

    // === Résultat de l'ajout ===
    private val _addAgentResult = mutableStateOf<Result<Unit>?>(null)
    val addAgentResult: State<Result<Unit>?> = _addAgentResult

    // === Champs de l'agent ===
    private val _agentName = mutableStateOf("")
    val agentName: State<String> = _agentName

    private val _agentEmail = mutableStateOf("")
    val agentEmail: State<String> = _agentEmail

    private val _agentPassword = mutableStateOf("")
    val agentPassword: State<String> = _agentPassword

    private val _agentSexe = mutableStateOf("")
    val agentSexe: State<String> = _agentSexe

    private val _agentDepartement = mutableStateOf("")
    val agentDepartement: State<String> = _agentDepartement

    // === Liste des agents du département sélectionné ===
    private val _agentsInDepartment = mutableStateOf<List<User>>(emptyList())
    val agentsInDepartment: State<List<User>> = _agentsInDepartment

    // === Setters ===
    fun updateAgentName(newValue: String) {
        _agentName.value = newValue
    }

    fun updateAgentPassword(newValue: String) {
        _agentPassword.value = newValue
    }

    fun updateAgentEmail(newValue: String) {
        _agentEmail.value = newValue
    }

    fun updateAgentSexe(newValue: String) {
        _agentSexe.value = newValue
    }

    fun updateAgentDepartement(newValue: String) {
        _agentDepartement.value = newValue
        loadAgentsForDepartment(newValue)
    }

    // === Ajouter l'agent ===
    fun addAgent() {
        val user = User(
            email = _agentEmail.value,
            password = _agentPassword.value,
            nom = _agentName.value,
            sexe = _agentSexe.value,
            departement = _agentDepartement.value,
            role = "agent"
        )

        viewModelScope.launch {
            _addAgentResult.value = userRepository.addUser(user)
        }
    }

    // === Charger tous les départements ===
    private fun loadDepartments() {
        viewModelScope.launch {
            val result = departmentRepository.getAllDepartments()
            if (result.isSuccess) {
                val departmentsList = result.getOrDefault(emptyList())
                _departments.value = departmentsList

                if (departmentsList.isNotEmpty() && _agentDepartement.value.isBlank()) {
                    val firstDepartmentId = departmentsList.first().id
                    _agentDepartement.value = firstDepartmentId
                    loadAgentsForDepartment(firstDepartmentId)
                }
            }
        }
    }

    // === Charger les agents d'un département ===
    private fun loadAgentsForDepartment(departmentId: String) {
        viewModelScope.launch {
            val result = userRepository.getAllUsers()
            if (result.isSuccess) {
                val agents = result.getOrDefault(emptyList())
                    .filter { it.role == "agent" && it.departement == departmentId }
                _agentsInDepartment.value = agents
            }
        }
    }

    fun resetAddAgentResult() {
        _addAgentResult.value = null
    }
}
