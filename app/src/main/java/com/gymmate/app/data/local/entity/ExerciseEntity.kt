package com.gymmate.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val muscleGroup: String,
    val workoutType: String,
    val difficulty: String,
    val durationSeconds: Int?,
    val reps: Int?,
    val sets: Int?,
    val imageUrl: String? = null,
    val animationAsset: String? = null
)