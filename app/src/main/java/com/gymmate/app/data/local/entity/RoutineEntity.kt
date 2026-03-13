package com.gymmate.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val workoutType: String,
    val difficulty: String,
    val durationMinutes: Int,
    val imageUrl: String? = null
)