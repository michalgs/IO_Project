package com.example.io_project.datamanagement
import com.example.io_project.dataclasses.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//lastcheck nie ustawiac

suspend fun addTaskToFirestore(userID: String, task: Task) {
    val firestore = FirebaseFirestore.getInstance()
    val userDocumentRef = firestore.collection("users").document(userID)
    val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy MM dd"))
    task.lastCheck = today
    try {
        userDocumentRef.update("tasks", FieldValue.arrayUnion(task)).await()
        println("Task added successfully.")
    } catch (e: Exception) {
        println("Error when adding task: ${e.message}")
        e.printStackTrace()
    }
}
