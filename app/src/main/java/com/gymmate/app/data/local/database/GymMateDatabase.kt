package com.gymmate.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gymmate.app.data.local.dao.ExerciseDao
import com.gymmate.app.data.local.dao.RoutineDao
import com.gymmate.app.data.local.dao.RoutineExerciseDao
import com.gymmate.app.data.local.dao.WorkoutSessionDao
import com.gymmate.app.data.local.entity.ExerciseEntity
import com.gymmate.app.data.local.entity.RoutineEntity
import com.gymmate.app.data.local.entity.RoutineExerciseEntity
import com.gymmate.app.data.local.entity.WorkoutSessionEntity

@Database(
    entities = [
        ExerciseEntity::class,
        RoutineEntity::class,
        RoutineExerciseEntity::class,
        WorkoutSessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GymMateDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun routineExerciseDao(): RoutineExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
}