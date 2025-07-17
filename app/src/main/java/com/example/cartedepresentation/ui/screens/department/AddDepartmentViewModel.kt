package com.example.cartedepresentation.ui.screens.department

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.Department
import com.example.cartedepresentation.data.model.User
import com.example.cartedepresentation.data.repository.DepartmentRepository
import com.example.cartedepresentation.data.repository.UserRepository
import kotlinx.coroutines.launch

class AddDepartmentViewModel(
    private val departmentRepository: DepartmentRepository = DepartmentRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    // === Champs du département ===
    private val _departmentId = mutableStateOf("")
    val departmentId: State<String> = _departmentId

    private val _departmentName = mutableStateOf("")
    val departmentName: State<String> = _departmentName

    private val _departmentDirecteur = mutableStateOf("")
    val departmentDirecteur: State<String> = _departmentDirecteur

    // === Résultat de l'opération ===
    private val _addDepartmentResult = mutableStateOf<Result<Unit>?>(null)
    val addDepartmentResult: State<Result<Unit>?> = _addDepartmentResult

    // === Tous les utilisateurs ===
    private var allUsers: List<User> = emptyList()

    // === Liste filtrée : directeurs possibles ===
    private val _directeurs = mutableStateOf<List<User>>(emptyList())
    val directeurs: State<List<User>> = _directeurs

    init {
        loadAllUsers()
    }

    // === SETTERS ===
    fun updateDepartmentName(newValue: String) {
        _departmentName.value = newValue
    }

    fun updateDepartmentDirecteur(newValue: String) {
        _departmentDirecteur.value = newValue
    }

    fun updateDepartmentId(newValue: String) {
        _departmentId.value = newValue
        filterDirecteursByDepartment(newValue)
    }

    fun loadDepartmentForEditing(department: Department) {
        _departmentId.value = department.id
        _departmentName.value = department.nom
        _departmentDirecteur.value = department.directeur
        filterDirecteursByDepartment(department.id)
    }

    fun addDepartment() {
        val newDepartmentName = _departmentName.value

        // Vérification basique
        if (newDepartmentName.isBlank()) {
            _addDepartmentResult.value = Result.failure(Exception("Veuillez remplir tous les champs."))
            return
        }

        val newDepartment = Department(
            nom = newDepartmentName,
        )

        viewModelScope.launch {
            val addResult = departmentRepository.addDepartment(newDepartment)
            _addDepartmentResult.value = addResult
        }
    }


    fun saveDepartment() {
        val currentDepartmentId = _departmentId.value
        val newDirecteurId = _departmentDirecteur.value

        viewModelScope.launch {
            // 1. Récupérer l'ancien département pour comparaison
            val oldDepartmentResult = departmentRepository.getDepartmentById(currentDepartmentId)

            if (oldDepartmentResult.isFailure) {
                _addDepartmentResult.value = Result.failure(Exception("Impossible de récupérer les données du département"))
                return@launch
            }

            val oldDepartment = oldDepartmentResult.getOrNull()
            val oldDirecteurId = oldDepartment?.directeur

            // 2. Mise à jour du département
            val updatedDepartment = Department(
                id = currentDepartmentId,
                nom = _departmentName.value,
                directeur = newDirecteurId
            )

            val updateResult = departmentRepository.updateDepartment(updatedDepartment)

            // 3. Mise à jour des rôles si directeur changé
            if (updateResult.isSuccess && oldDirecteurId != null && oldDirecteurId != newDirecteurId) {
                // Ancien directeur : devient agent
                userRepository.updateUserRole(oldDirecteurId, "agent")

                // Nouveau directeur : devient directeur
                userRepository.updateUserRole(newDirecteurId, "directeur")
            }

            _addDepartmentResult.value = updateResult
        }
    }


    private fun loadAllUsers() {
        viewModelScope.launch {
            val result = userRepository.getAllUsers()
            if (result.isSuccess) {
                allUsers = result.getOrDefault(emptyList())
                // Si un ID de département est déjà présent, on filtre
                if (_departmentId.value.isNotBlank()) {
                    filterDirecteursByDepartment(_departmentId.value)
                }
            }
        }
    }

    private fun filterDirecteursByDepartment(departmentId: String) {
        if (departmentId.isBlank()) return
        val filtered = allUsers.filter { it.departement == departmentId }
        _directeurs.value = filtered

        // Auto-préselection si aucun directeur défini
        if (_departmentDirecteur.value.isBlank() && filtered.isNotEmpty()) {
            _departmentDirecteur.value = filtered.first().id
        }
    }

    fun resetAddDepartmentResult() {
        _addDepartmentResult.value = null
    }
}
