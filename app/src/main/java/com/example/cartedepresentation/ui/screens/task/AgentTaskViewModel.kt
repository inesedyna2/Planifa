package com.example.cartedepresentation.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartedepresentation.data.model.Task
import com.example.cartedepresentation.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AgentTaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    var currentAgentId: String = ""
    var currentDepartmentId: String = ""

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getTasksByEmployee(currentAgentId)
        }
    }

    fun addTask(description: String, dateDebut: String, dateFin: String, statut: String) {
        viewModelScope.launch {
            val task = Task(
                id = UUID.randomUUID().toString(),
                description = description,
                dateDebut = dateDebut,
                dateFin = dateFin,
                statut = statut,
                employeId = currentAgentId
            )
            repository.addTask(task)
            loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks()
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
            loadTasks()
        }
    }
}
