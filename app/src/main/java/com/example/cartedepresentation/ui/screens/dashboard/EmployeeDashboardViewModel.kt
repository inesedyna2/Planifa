package com.example.cartedepresentation.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.Task
import com.example.cartedepresentation.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeDashboardViewModel(
    private val repository: TaskRepository = TaskRepository() // injection simple
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    var currentUserId: String = ""

    fun loadTasks() {
        if (currentUserId.isBlank()) return

        viewModelScope.launch {
            val result = repository.getTasksByEmployee(currentUserId)
            _tasks.value = result
        }
    }

    fun updateTaskStatus(taskId: String, newStatus: String) {
        viewModelScope.launch {
            // Trouver la tâche à mettre à jour
            val taskToUpdate = _tasks.value.find { it.id == taskId }
            if (taskToUpdate != null) {
                val updatedTask = taskToUpdate.copy(statut = newStatus)
                val result = repository.updateTask(updatedTask)
                if (result.isSuccess) {
                    _tasks.value = _tasks.value.map {
                        if (it.id == taskId) it.copy(statut = newStatus) else it
                    }
                }
            }
        }
    }
}
