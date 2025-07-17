package com.example.cartedepresentation.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.Department
import com.example.cartedepresentation.data.repository.DepartmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminDashboardViewModel(
    private val repository: DepartmentRepository = DepartmentRepository()
) : ViewModel() {

    private val _departments = MutableStateFlow<List<Department>>(emptyList())
    val departments: StateFlow<List<Department>> = _departments

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadDepartments()
    }

    private fun loadDepartments() {
        viewModelScope.launch {
            val result = repository.getAllDepartments()
            result.onSuccess {
                _departments.value = it
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }
}
