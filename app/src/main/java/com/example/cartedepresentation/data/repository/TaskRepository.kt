package com.example.cartedepresentation.data.repository

import com.example.cartedepresentation.data.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class TaskRepository {

    private val db = FirebaseFirestore.getInstance()
    private val taskCollection = db.collection("tasks")

    // ✅ Ajouter une tâche
    suspend fun addTask(task: Task): Result<Unit> {
        return try {
            taskCollection.document(task.id).set(task).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ✅ Mettre à jour une tâche
    suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            taskCollection.document(task.id).set(task).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ✅ Supprimer une tâche
    suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            taskCollection.document(taskId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ✅ Obtenir toutes les tâches d’un département
    suspend fun getTasksByDepartment(departmentId: String): List<Task> {
        return try {
            taskCollection
                .whereEqualTo("departementId", departmentId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Task::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ✅ Obtenir les tâches d’un employé
    suspend fun getTasksByEmployee(employeeId: String): List<Task> {
        return try {
            taskCollection
                .whereEqualTo("employeId", employeeId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Task::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
