package com.example.cartedepresentation.data.repository

import com.example.cartedepresentation.data.model.Department
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DepartmentRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val departmentsCollection = firestore.collection("departments")

    // Ajouter un département
    suspend fun addDepartment(department: Department): Result<Unit> {
        return try {
            println("DepartmentRepository - Début de l'ajout") // Ajout d'un log
            val documentRef = departmentsCollection.document()
            val departmentWithId = department.copy(id = documentRef.id)
            documentRef.set(departmentWithId).await()
            println("DepartmentRepository - Ajout réussi") // Ajout d'un log
            Result.success(Unit)
        } catch (e: Exception) {
            println("DepartmentRepository - Erreur lors de l'ajout: ${e.message}") // Ajout d'un log en cas d'erreur
            Result.failure(e)
        }
    }


    // Récupérer un département par son ID
    suspend fun getDepartmentById(id: String): Result<Department?> {
        return try {
            val snapshot = departmentsCollection.document(id).get().await()
            val department = snapshot.toObject(Department::class.java)
            Result.success(department)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Récupérer tous les départements
    suspend fun getAllDepartments(): Result<List<Department>> {
        return try {
            val snapshot = departmentsCollection.get().await()
            val departments = snapshot.documents.mapNotNull { it.toObject(Department::class.java) }
            Result.success(departments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Supprimer un département
    suspend fun deleteDepartment(id: String): Result<Unit> {
        return try {
            departmentsCollection.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Mettre à jour un département
    suspend fun updateDepartment(department: Department): Result<Unit> {
        return try {
            departmentsCollection.document(department.id).set(department).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
