package com.example.cartedepresentation.data.repository

import com.example.cartedepresentation.data.model.Department
import com.example.cartedepresentation.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val auth = FirebaseAuth.getInstance()

    suspend fun addUser(user: User): Result<Unit> {
        return try {
            println("UserRepository - Création de l'utilisateur Firebase Auth...")

            // Étape 1 : Créer l'utilisateur dans Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            val uid = authResult.user?.uid ?: throw Exception("Échec de la création de l'utilisateur Auth")

            println("UserRepository - Utilisateur Firebase Auth créé avec UID : $uid")

            //Étape 1 : Créer un objet User sans l'email et le mot de passe pour Firestore
            // 2. Construire manuellement une map sans email et password
            val newUser = mutableMapOf<String, Any>(
                "id" to uid,
                "nom" to user.nom,
                "role" to user.role,
                "sexe" to user.sexe,
                "password" to user.password,
                "departement" to user.departement
            )
            usersCollection.document(uid).set(newUser).await()

            println("UserRepository - Utilisateur enregistré dans Firestore")
            Result.success(Unit)

        } catch (e: Exception) {
            println("UserRepository - Erreur lors de l'ajout: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: String): Result<User?> {
        return try {
            val snapshot = usersCollection.document(userId).get().await()
            val user = snapshot.toObject<User>()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllUsers(): Result<List<User>> {
        return try {
            val snapshot = usersCollection.get().await()
            val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsersByRole(role: String): Result<List<User>> {
        return try {
            val snapshot = usersCollection.whereEqualTo("role", role).get().await()
            val users = snapshot.documents.mapNotNull { it.toObject<User>() }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsersByDepartment(departmentId: String): Result<List<User>> {
        return try {
            val snapshot = usersCollection
                .whereEqualTo("departement", departmentId)
                .get()
                .await()

            val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            usersCollection.document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserRole(userId: String, newRole: String): Result<Unit> {
        return try {
            usersCollection.document(userId).update("role", newRole).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
